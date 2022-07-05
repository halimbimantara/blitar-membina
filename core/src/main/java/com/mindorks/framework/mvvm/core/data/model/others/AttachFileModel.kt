package com.mindorks.framework.mvvm.core.data.model.others

import android.net.Uri

class AttachFileModel {
    var fileName: String? = null
    var originalPath: String? = null
    var compressedPath: String? = null
    var type: String? = null
    var previewThumbnail: String? = null
    var extension: String? = null
    var size: Long = 0
    var id = 0
    var cameraImageUri: Uri? = null
    var queryUri: String? = null
}