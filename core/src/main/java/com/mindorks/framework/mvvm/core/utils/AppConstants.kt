package com.mindorks.framework.mvvm.core.utils

object AppConstants {
    const val TIME_OUT_CONNECTION = 30L
    const val API_STATUS_CODE_LOCAL_ERROR = 0
    const val DB_NAME = "mindorks_mvvm.db"
    const val NULL_INDEX = -1L
    const val PREF_NAME = "mindorks_pref"
    const val SEED_DATABASE_OPTIONS = "seed/options.json"
    const val SEED_DATABASE_QUESTIONS = "seed/questions.json"
    const val STATUS_CODE_FAILED = "failed"
    const val STATUS_CODE_SUCCESS = "success"
    const val TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss"
    const val DATE_FORMAT_STANDARD = "yyyy-MM-dd_HHmmss"
    const val DATE_FORMAT_MY = "MM/yyyy"
    const val DATE_FORMAT_REVIEW = "dd MMMM yyyy"
    const val DATE_FORMAT_RECOMMENDATION = "dd MMM yyyy"
    const val DATE_FORMAT_MDY = "MMMM dd yyyy"
    const val DATE_FORMAT_MDY_KOMA = "MMMM dd, yyyy"
    const val DATE_FORMAT_MDY_KOMA_2 = "MMM dd, yyyy"
    const val DATE_FORMAT_MDY_TIMESTAMP = "MMMM dd yyyy HH:mm:ss"
    const val DATE_FORMAT_MDY_TIMESTAMP_KOMA = "MMMM dd, yyyy HH:mm:ss"
    const val DATE_FORMAT_TIMESTAMP = "dd MMMM yyyy HH:mm:ss"
    const val DATE_FORMAT_EVENT = "dd MMMM yyyy, HH:mm"
    const val DATE_FORMAT_EVENT_MDY = "MMM dd, yyyy, HH:mm"
    const val DATE_FORMAT_EVENT_MDY_DETAIL = "MMMM dd, yyyy, HH:mm"
    const val DATE_FORMAT_EVENT_DETAIL = "dd MMMM yyyy"
    const val DATE_FORMAT_EVENT_HOUR = "HH:mm"
    const val DATE_FORMAT_EVENT_TIME = "HH:mm:ss"
    const val DATE_FORMAT_DAYNAME = "EEEE"
    const val DATE_FORMAT_YEAR = "yyyy"
    const val DATE_FORMAT_MONTH_YEAR = "MMM yyyy"
    const val DATE_FORMAT_MONTH = "MM"
    const val DATE_FORMAT_DAY_OF_MONTH = "dd"
    const val DATE_FORMAT_PERFORMANCE = "dd/MM/yyyy"
    const val DATE_FORMAT_PERFORMANCE_V2 = "ddMMyyyy"
    const val DATE_FORMAT_ATTENDANCE_LIST_PERIODE = "dd MMM yyyy"
    const val DATE_FORMAT_ATTENDANCE_LIST_LEAVE = "MMM dd, yyyy"
    const val DATE_FORMAT_ATTENDANCE_LIST = "EEEE, dd MMMM yyyy"
    const val DATE_FORMAT_STANDARD_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    const val DATE_FORMAT_STANDARD_ISO_DEFAULT = "yyyy-MM-dd'T'HH:mm:ss"
    const val DATE_FORMAT_STANDARD_ISO_Z = "yyyy-MM-dd'T'HH:mm'Z'"
    const val DATE_FORMAT_STANDARD_ISO_Z_2 = "yyyy-M-d'T'HH:mm:ss.SSS'Z''"
    const val DATE_FORMAT_STANDARD_ISO_2 = "EEE MMM dd HH:mm:ss zzz yyyy"
    const val DATE_FORMAT_STANDARD_ISO_3 = "EEE MMM dd HH:mm:ss zzzz yyyy"
    const val TIMESTAMP_FORMAT_STANDARD = "yyyy-MM-dd HH:mm:ss"
    const val DATE_FORMAT_DEFAULT = "yyyy-MM-dd"
    const val DATE_FORMAT_DEFAULT_1 = "yyyy/MM/dd"

    //gender
    const val USER_GENDER_MALE = "MALE"
    const val USER_GENDER_FEMALE = "FEMALE"

    //error
    var ERROR_TYPE_NETWORK = "error_network"
    var ERROR_TYPE_OTHER = "error_other"
    var ERROR_TYPE_TOKEN_FAILED = "error_token"
}