package com.mindorks.framework.mvvm.ui.features.pembina.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kbeanie.multipicker.api.*
import com.kbeanie.multipicker.api.callbacks.FilePickerCallback
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback
import com.kbeanie.multipicker.api.entity.ChosenFile
import com.kbeanie.multipicker.api.entity.ChosenImage
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.core.data.model.api.response.ListCategoryItem
import com.mindorks.framework.mvvm.core.data.model.api.service.CategoryService
import com.mindorks.framework.mvvm.core.data.model.others.AttachFileModel
import com.mindorks.framework.mvvm.core.data.remote.NetworkErrorHandlingTag
import com.mindorks.framework.mvvm.core.ui.common.extensions.etToString
import com.mindorks.framework.mvvm.core.utils.AppConstants
import com.mindorks.framework.mvvm.core.utils.AppUtils
import com.mindorks.framework.mvvm.core.utils.rx.observe
import com.mindorks.framework.mvvm.databinding.ActivityFormLowonganBinding
import com.mindorks.framework.mvvm.di.component.ActivityComponent
import com.mindorks.framework.mvvm.ui.account.login.register.CompletionAdapter
import com.mindorks.framework.mvvm.ui.base.BaseActivity
import com.mindorks.framework.mvvm.ui.features.pembina.viewmodels.FormPostViewModel
import com.mindorks.framework.mvvm.ui.helper.adapter.ListAttachmentFileAdapter
import com.mindorks.framework.mvvm.ui.helper.adapter.ListAttachmentFileImageAdapter
import com.mindorks.framework.mvvm.ui.helper.adapter.ListAttachmentImageAdapter
import com.mindorks.framework.mvvm.utils.widget.ChooseFileDialog
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.mindorks.framework.mvvm.utils.ext.gone
import com.mindorks.framework.mvvm.utils.ext.visible
import timber.log.Timber
import java.io.File
import java.util.ArrayList
import java.util.HashMap

class FormPostLokerActivity : BaseActivity<ActivityFormLowonganBinding?, FormPostViewModel?>(),
    ChooseFileDialog.DialogItemListener, ListAttachmentImageAdapter.ImageAttachListListener,
    ListAttachmentFileAdapter.FileAttachmentListener, ImagePickerCallback, FilePickerCallback {
    private lateinit var adapter: CompletionAdapter
    private var isGranted: Boolean = false
    private var isCompressedFiled: Boolean = false
    lateinit var binding: ActivityFormLowonganBinding

    //file picker
    private var cameraPicker: CameraImagePicker? = null
    private var imagePicker: ImagePicker? = null
    private var filePicker: FilePicker? = null
    private var pickerPath: String? = null
    private var adapterImageAttach: ListAttachmentImageAdapter? = null
    private var imageAttachAdapter: ListAttachmentFileImageAdapter? = null
    private var fileAttachAdapter: ListAttachmentFileAdapter? = null
    private val urlList: MutableList<AttachFileModel> = ArrayList()
    private val modelListImage: MutableList<AttachFileModel> = ArrayList()
    private val modelListFile: MutableList<AttachFileModel> = ArrayList()
    private val uploaded: MutableList<HashMap<String, String>> = ArrayList()
    var dialogFile: ChooseFileDialog? = null
    var modelPost = HashMap<String, Any>()
    var categoryService: ArrayList<CategoryService> = arrayListOf()
    var mFile: File? = null
    val listKategori: ArrayList<String> = arrayListOf()
    var categoryId = 0

    override val bindingVariable: Int
        get() = 0

    override val layoutId: Int
        get() = R.layout.activity_form_lowongan

    private val titleCourse by lazy {
        intent.getStringExtra(EXTRA_TITLE)
    }
    private val accessTipe by lazy {
        intent.getStringExtra(EXTRA_TIPE)
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    var permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    companion object {
        const val EXTRA_TITLE = "EXTRA_TITLE"

        /**
         * tipe student,pembina
         */
        const val EXTRA_TIPE = "EXTRA_TIPE"

        fun newIntent(context: Context?, mTitle: String, tipe: String? = null): Intent {
            return Intent(context, FormPostLokerActivity::class.java).apply {
                putExtra(EXTRA_TITLE, mTitle)
                putExtra(EXTRA_TIPE, tipe)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLowonganBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = titleCourse
        initUI()
        actionUI()
    }

    private fun actionUI() {
        binding.chooseFile.setOnClickListener {
            dialogFile?.showDialog()
        }

        binding.BtnApply.setOnClickListener {
            formValidation()

        }

        binding.spinKategori.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView: AdapterView<*>?, view: View?, i: Int, l: Long ->
                if (i > 0) {
                    val pos = i - 1
                    categoryId = categoryService[pos].id!!.toInt()
                }
            }
    }

    fun EditText.showErrorInfo() {
        this.error = getString(R.string.info_field_canempty)
    }

    private fun formValidation() {
        modelPost = HashMap<String, Any>()
        val title = binding.EtNamaLowongan.etToString()
        val quota = binding.etQuota.etToString()
        val nmTmptUsaha = binding.EtNamaTmptUsaha.etToString()//desc
        val materi = binding.EtDescription.etToString()//materi
        val alamat = binding.EtAlamatPostingLoker.etToString()//
        val jadwal = binding.EtJadwal.etToString()//

        if (title.isEmpty()) {
            binding.EtNamaLowongan.showErrorInfo()
        } else if (quota.isEmpty()) {
            binding.etQuota.showErrorInfo()
        } else if (nmTmptUsaha.isEmpty()) {
            binding.EtNamaTmptUsaha.showErrorInfo()
        } else if (materi.isEmpty()) {
            binding.EtDescription.showErrorInfo()
        } else if (alamat.isEmpty()) {
            binding.EtAlamatPostingLoker.showErrorInfo()
        } else if (alamat.isEmpty()) {
            binding.EtAlamatPostingLoker.showErrorInfo()
        } else if (jadwal.isEmpty()) {
            binding.EtJadwal.showErrorInfo()
        } else if (quota.isEmpty() || quota.toInt() == 0) {
            binding.etQuota.showErrorInfo()
        } else if (categoryId <= 0) {
            showMessage(getString(R.string.info_field_category_empty))
        } else if (mFile?.isFile == false) {
            showMessage(getString(R.string.info_field_pict_empty))
        } else {
            modelPost["name"] = title
            modelPost["nama_usaha"] = nmTmptUsaha
            modelPost["alamat_lowongan"] = alamat
            modelPost["jadwal"] = jadwal
            modelPost["category_id"] = categoryId.toString()
            modelPost["provider_id"] = mViewModel?.dataManager?.currentUserId!!.toString()
            modelPost["type"] = "fixed"
            modelPost["discount"] = "0"
            modelPost["duration"] = "00:00"
            modelPost["description"] = materi
            modelPost["is_featured"] = "0"
            modelPost["status"] = "0"
            modelPost["price"] = "0"
            modelPost["quota"] = quota
            showLoadingProgress(true)
            mViewModel!!.postServiceLoker(modelPost, mFile!!)
        }
    }

    private fun initUI() {
        listKategori.add("-- Pilih Kategori --")
        adapterImageAttach = ListAttachmentImageAdapter(this)
        adapterImageAttach!!.setListener(this)
        fileAttachAdapter = ListAttachmentFileAdapter(this)
        fileAttachAdapter!!.setListener(this)
        dialogFile = ChooseFileDialog(this)
        dialogFile!!.setListener(this)

        binding.EtNamaPembina.setText(mViewModel?.dataManager?.currentUserName)

        //init list category
        adapter = CompletionAdapter(
            this, R.layout.item_drop_downl,
            listKategori
        )
        binding.spinKategori.setAdapter(adapter)
        binding.spinKategori.hint = "-- Pilih Kategori --"
    }

    /**
     * File picker image,file,gallery
     */
    private fun takePicture() {
        Permissions.check(this, permissions, null, null, object : PermissionHandler() {
            override fun onGranted() {
                cameraPicker = CameraImagePicker(this@FormPostLokerActivity)
                cameraPicker!!.setDebugglable(true)
                cameraPicker!!.setCacheLocation(CacheLocation.EXTERNAL_STORAGE_APP_DIR)
                cameraPicker!!.setImagePickerCallback(object : ImagePickerCallback {
                    override fun onError(p0: String?) {
                        showMessage(getString(R.string.info_alert_file_cannot_read) + "\n $p0")
                    }

                    override fun onImagesChosen(list: MutableList<ChosenImage>?) {
                        for (b in list!!.indices) {
                            if (list[b].size >= AppUtils.FILE_MAX_SIZE_UPLOAD) {
                                Toast.makeText(
                                    this@FormPostLokerActivity,
                                    getString(R.string.info_alert_file_max),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                mFile = File(list[b].originalPath)
                                mViewModel!!.compressImage(
                                    this@FormPostLokerActivity,
                                    list[b].originalPath
                                )
                                val model = AttachFileModel()
                                model.fileName = list[b].displayName
                                model.originalPath = list[b].originalPath
                                model.previewThumbnail = list[b].thumbnailPath
                                modelListImage.add(model)
                            }
                        }
                        binding.rvListImage.visibility = View.VISIBLE
                        binding.rvListImage.layoutManager =
                            LinearLayoutManager(
                                this@FormPostLokerActivity,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        binding.rvListImage.adapter = adapterImageAttach
                        adapterImageAttach!!.setPosts(modelListImage)
                    }

                })
                cameraPicker!!.shouldGenerateMetadata(true)
                cameraPicker!!.shouldGenerateThumbnails(true)
                pickerPath = cameraPicker!!.pickImage()
            }
        })
    }

    private fun filePick() {
        Permissions.check(this, permissions, null, null, object : PermissionHandler() {
            override fun onGranted() {
                filePicker = FilePicker(this@FormPostLokerActivity)
                filePicker!!.setFilePickerCallback(object : FilePickerCallback {
                    override fun onError(p0: String?) {
                    }

                    override fun onFilesChosen(list: MutableList<ChosenFile>?) {
                        for (b in list!!.indices) {
                            if (list[b].size >= AppUtils.FILE_MAX_SIZE_UPLOAD) {
                                Toast.makeText(
                                    this@FormPostLokerActivity,
                                    getString(R.string.info_alert_file_max),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                // Currently can't compress File (Tryout)

                                val model = AttachFileModel()
                                model.fileName = list[b].displayName
                                model.originalPath = list[b].originalPath
                                urlList.add(model)
                                modelListFile.add(model)
                            }
                        }
                        binding.rvListFile.visibility = View.VISIBLE
                        binding.rvListFile.layoutManager =
                            LinearLayoutManager(
                                this@FormPostLokerActivity,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        binding.rvListFile.adapter = fileAttachAdapter
                        fileAttachAdapter!!.setPosts(modelListFile)
                    }

                })
                filePicker!!.setMimeType("application/*")
                filePicker!!.allowMultiple()
                filePicker!!.pickFile()
            }
        })
    }

    private fun imagePick() {
        Permissions.check(this, permissions, null, null, object : PermissionHandler() {
            override fun onGranted() {
                imagePicker = ImagePicker(this@FormPostLokerActivity)
                imagePicker!!.setImagePickerCallback(object : ImagePickerCallback {
                    override fun onError(errInfo: String?) {
                        showMessage(getString(R.string.info_alert_file_cannot_read) + "\n $errInfo")
                    }

                    override fun onImagesChosen(list: MutableList<ChosenImage>?) {
                        for (b in list!!.indices) {
                            if (list[b].size >= AppUtils.FILE_MAX_SIZE_UPLOAD) {
                                Toast.makeText(
                                    this@FormPostLokerActivity,
                                    getString(R.string.info_alert_file_max),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
//                                viewModel!!.compressImage(this, list[b].originalPath)
                                val model = AttachFileModel()
                                model.fileName = list[b].displayName
                                model.originalPath = list[b].originalPath
                                model.previewThumbnail = list[b].thumbnailPath
                                modelListImage.add(model)
                            }
                        }
                        binding.rvListImage.visibility = View.VISIBLE
                        binding.rvListImage.layoutManager =
                            LinearLayoutManager(
                                this@FormPostLokerActivity,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        binding.rvListImage.adapter = adapterImageAttach
                        adapterImageAttach!!.setPosts(modelListImage)
                    }

                })
                imagePicker!!.allowMultiple()
                imagePicker!!.pickImage()
            }
        })
    }


    override fun observeChange() {
        observe(mViewModel!!.getListCategoryItem, ::itemListCategory)
        observe(mViewModel!!.fileCompress, ::getFileCompressed)
        observe(mViewModel!!.successPostLoker, ::successPostLowongan)
        observe(mViewModel!!.errorData, ::errorHandling)
    }

    private fun errorHandling(errorResponse: NetworkErrorHandlingTag) {
        when (errorResponse.tipeError) {
            AppConstants.ERROR_TYPE_NETWORK -> {
                Timber.i("Network Error")
                showMessage("${errorResponse.messages}")
            }

            AppConstants.ERROR_TYPE_TOKEN_FAILED -> {
                Timber.i("Regenerate token")
                showMessage("Regenerate token ${errorResponse.messages}")
            }

            AppConstants.ERROR_TYPE_OTHER -> {
                Timber.i("Failed ${errorResponse.messages}")
                showMessage("Failed ${errorResponse.messages}")
            }
        }
        showLoadingProgress(false)
    }

    private fun successPostLowongan(b: Boolean) {
        showLoadingProgress(!b)//if true hide
    }

    private fun showLoadingProgress(show: Boolean) {
        if (show) {
            binding.BtnApply.gone()
            binding.CLoading.visible()
        } else {
            binding.BtnApply.visible()
            binding.CLoading.gone()
        }
    }

    private fun getFileCompressed(file: File) {
        if (file != null) {
            mFile = file
            isCompressedFiled = true
            binding.BtnApply.isEnabled = true
        }
    }

    private fun itemListCategory(luis: ListCategoryItem?) {
        categoryService.clear()
        listKategori.clear()
        if (luis!!.data?.isNotEmpty() == true) {
            listKategori.add("-- Pilih Kategori --")
            for (i in 0 until luis.data!!.size) {
                categoryService.add(CategoryService(luis.data!![i]?.id, luis.data!![i]?.name))
                listKategori.add(luis.data!![i]?.name.toString())
            }
            adapter.notifyDataSetChanged()
        }
    }

    /**
     * Choose Dialog prompt
     */
    override fun onItemClick(view: View?) {
        dialogFile!!.hideDialog()
        if (configPermission()) {
            when (view?.id) {
                R.id.camera -> {
                    takePicture()
                }
                R.id.gallery -> {
                    imagePick()
                }
                R.id.uploadFile -> {
                    filePick()
                }
            }
        }
    }

    private fun configPermission(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (AppUtils.setPermissions(this, permissions, "-")) {
            isGranted = true
            showMessage(resources.getString(R.string.alert_acces_granted))
        } else {
            isGranted = false
        }
        return isGranted
    }

    override fun onDeleteItemImage(pos: Int) {

    }

    override fun onDeleteItemFile(pos: Int) {

    }


    public override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                Picker.PICK_IMAGE_DEVICE -> {
                    if (imagePicker == null) {
                        imagePicker = ImagePicker(this)
                        imagePicker!!.setImagePickerCallback(this)
                    }
                    imagePicker!!.submit(data)
                }
                Picker.PICK_FILE -> {
                    if (filePicker == null) {
                        filePicker = FilePicker(this)
                        filePicker!!.setFilePickerCallback(this)
                    }
                    filePicker!!.submit(data)
                }
                Picker.PICK_IMAGE_CAMERA -> {
                    if (cameraPicker == null) {
                        cameraPicker = CameraImagePicker(this)
                        cameraPicker!!.setImagePickerCallback(this)
                        cameraPicker!!.reinitialize(pickerPath)
                    }
                    cameraPicker!!.submit(data)
                }
            }
        } else {
            showMessage("You haven't picked File")
        }
    }

    override fun onError(p0: String?) {

    }

    override fun onFilesChosen(list: MutableList<ChosenFile>?) {
        for (b in list!!.indices) {
            if (list[b].size >= AppUtils.FILE_MAX_SIZE_UPLOAD) {
                Toast.makeText(
                    this,
                    getString(R.string.info_alert_file_max),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mFile = File(list[b].originalPath)
                val model = AttachFileModel()
                model.fileName = list[b].displayName
                model.originalPath = list[b].originalPath
                urlList.add(model)
                modelListFile.add(model)
            }
        }
        binding.rvListFile.visibility = View.VISIBLE
        binding.rvListFile.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvListFile.adapter = fileAttachAdapter
        fileAttachAdapter!!.setPosts(modelListFile)
    }

    override fun onImagesChosen(list: MutableList<ChosenImage>?) {
        for (b in list!!.indices) {
            if (list[b].size >= AppUtils.FILE_MAX_SIZE_UPLOAD) {
                Toast.makeText(
                    this,
                    getString(R.string.info_alert_file_max),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mFile = File(list[b].originalPath)
                mViewModel!!.compressImage(this, list[b].originalPath)
                val model = AttachFileModel()
                model.fileName = list[b].displayName
                model.originalPath = list[b].originalPath
                model.previewThumbnail = list[b].thumbnailPath
                modelListImage.add(model)
            }
        }
        binding.rvListImage.visibility = View.VISIBLE
        binding.rvListImage.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvListImage.adapter = adapterImageAttach
        adapterImageAttach!!.setPosts(modelListImage)
    }

}