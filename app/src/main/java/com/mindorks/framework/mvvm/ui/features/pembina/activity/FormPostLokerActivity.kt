package com.mindorks.framework.mvvm.ui.features.pembina.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kbeanie.multipicker.api.*
import com.kbeanie.multipicker.api.callbacks.FilePickerCallback
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback
import com.kbeanie.multipicker.api.entity.ChosenFile
import com.kbeanie.multipicker.api.entity.ChosenImage
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.core.data.model.others.AttachFileModel
import com.mindorks.framework.mvvm.core.utils.AppUtils
import com.mindorks.framework.mvvm.databinding.ActivityFormLowonganBinding
import com.mindorks.framework.mvvm.di.component.ActivityComponent
import com.mindorks.framework.mvvm.ui.base.BaseActivity
import com.mindorks.framework.mvvm.ui.features.pembina.viewmodels.FormPostViewModel
import com.mindorks.framework.mvvm.ui.helper.adapter.ListAttachmentFileAdapter
import com.mindorks.framework.mvvm.ui.helper.adapter.ListAttachmentFileImageAdapter
import com.mindorks.framework.mvvm.ui.helper.adapter.ListAttachmentImageAdapter
import com.mindorks.framework.mvvm.utils.widget.ChooseFileDialog
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import java.io.File
import java.util.ArrayList
import java.util.HashMap

class FormPostLokerActivity : BaseActivity<ActivityFormLowonganBinding?, FormPostViewModel?>(),
    ChooseFileDialog.DialogItemListener, ListAttachmentImageAdapter.ImageAttachListListener,
    ListAttachmentFileAdapter.FileAttachmentListener, ImagePickerCallback, FilePickerCallback{
    private var isGranted: Boolean = false
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
    }

    private fun initUI() {
        adapterImageAttach = ListAttachmentImageAdapter(this)
        adapterImageAttach!!.setListener(this)
        fileAttachAdapter = ListAttachmentFileAdapter(this)
        fileAttachAdapter!!.setListener(this)
        dialogFile = ChooseFileDialog(this)
        dialogFile!!.setListener(this)
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


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
//                viewModel!!.compressImage(this, list[b].originalPath)
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