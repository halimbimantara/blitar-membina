package com.mindorks.framework.mvvm.ui.features.pembina.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kbeanie.multipicker.api.CacheLocation
import com.kbeanie.multipicker.api.CameraImagePicker
import com.kbeanie.multipicker.api.FilePicker
import com.kbeanie.multipicker.api.ImagePicker
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
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions

class FormPostLokerActivity : BaseActivity<ActivityFormLowonganBinding?, FormPostViewModel?>() {
    lateinit var binding: ActivityFormLowonganBinding

    //file picker
    private var cameraPicker: CameraImagePicker? = null
    private var imagePicker: ImagePicker? = null
    private var filePicker: FilePicker? = null
    private var pickerPath: String? = null

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
        buildComponent!!.inject(this)
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
    }

    /**
     * File picker image,file,gallery
     */
    fun takePicture() {
        Permissions.check(this, permissions, null, null, object : PermissionHandler() {
            override fun onGranted() {
                cameraPicker = CameraImagePicker(this@FormPostLokerActivity)
                cameraPicker!!.setDebugglable(true)
                cameraPicker!!.setCacheLocation(CacheLocation.EXTERNAL_STORAGE_APP_DIR)
                cameraPicker!!.setImagePickerCallback(object : ImagePickerCallback {
                    override fun onError(p0: String?) {

                    }

                    override fun onImagesChosen(p0: MutableList<ChosenImage>?) {

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

                    override fun onFilesChosen(p0: MutableList<ChosenFile>?) {

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
                    override fun onError(p0: String?) {

                    }

                    override fun onImagesChosen(list: MutableList<ChosenImage>?) {
                        for (b in list!!.indices) {
                            if (list[b].size >= AppUtils.FILE_MAX_SIZE_UPLOAD) {
//                                Toast.makeText(
//                                    this,
//                                    getStringFromResources(R.string.info_alert_file_max),
//                                    Toast.LENGTH_SHORT
//                                ).show()
                            } else {
//                                viewModel!!.compressImage(this, list[b].originalPath)
//                                val model = AttachFileModel()
//                                model.fileName = list[b].displayName
//                                model.originalPath = list[b].originalPath
//                                model.previewThumbnail = list[b].thumbnailPath
//                                modelListImage.add(model)
                            }
                        }
                        binding.rvListImage.visibility = View.VISIBLE
                        binding.rvListImage.layoutManager =
                            LinearLayoutManager(
                                this@FormPostLokerActivity,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
//                        binding.rvListImage.adapter = adapter
//                        adapter!!.setPosts(modelListImage)
                    }

                })
                imagePicker!!.allowMultiple()
                imagePicker!!.pickImage()
            }
        })
    }


    override fun observeChange() {

    }
}