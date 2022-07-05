package com.mindorks.framework.mvvm.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceUtils {
    const val EXTRA_TYPE_ROLE_PEMBINA = "extra_role_pembina"
    const val EXTRA_TYPE_ROLE_PELAMAR = "extra_role_pelamar"
    private const val PREFERENCE_KEY_USER_ROLE = "userRole"
    private const val PREFERENCE_KEY_USER_ID = "userId"
    private const val PREFERENCE_KEY_USER_TOKEN = "userToken"
    private const val PREFERENCE_KEY_NICKNAME = "nickname"
    private const val PREFERENCE_KEY_CONNECTED = "connected"
    private const val PREFERENCE_KEY_NOTIFICATIONS = "notifications"
    private const val PREFERENCE_KEY_NOTIFICATIONS_SHOW_PREVIEWS = "notificationsShowPreviews"
    private const val PREFERENCE_KEY_NOTIFICATIONS_DO_NOT_DISTURB = "notificationsDoNotDisturb"
    private const val PREFERENCE_KEY_NOTIFICATIONS_DO_NOT_DISTURB_FROM =
        "notificationsDoNotDisturbFrom"
    private const val PREFERENCE_KEY_NOTIFICATIONS_DO_NOT_DISTURB_TO = "notificationsDoNotDisturbTo"
    private const val PREFERENCE_KEY_GROUP_CHANNEL_DISTINCT = "channelDistinct"
    private const val PREFERENCES_IS_TOKEN_STORED = "is.token.stored"
    private const val PREFERENCES_TOKEN_INSTANCES = "token.instances"
    private var mAppContext: Context? = null

    fun init(appContext: Context?) {
        mAppContext = appContext
    }

    private val sharedPreferences: SharedPreferences
        private get() = mAppContext!!.getSharedPreferences("sendbird", Context.MODE_PRIVATE)

    var userId: String?
        get() = sharedPreferences.getString(PREFERENCE_KEY_USER_ID, "")
        set(userId) {
            val editor = sharedPreferences.edit()
            editor.putString(PREFERENCE_KEY_USER_ID, userId).apply()
        }
    var nickname: String?
        get() = sharedPreferences.getString(PREFERENCE_KEY_NICKNAME, "")
        set(nickname) {
            val editor = sharedPreferences.edit()
            editor.putString(PREFERENCE_KEY_NICKNAME, nickname).apply()
        }

    var userRole: String?
        get() = sharedPreferences.getString(PREFERENCE_KEY_USER_ROLE, "")
        set(user_roles) {
            val editor = sharedPreferences.edit()
            editor.putString(PREFERENCE_KEY_USER_ROLE, user_roles).apply()
        }
    var token: String?
        get() = sharedPreferences.getString(PREFERENCE_KEY_USER_TOKEN, "")
        set(token) {
            val editor = sharedPreferences.edit()
            editor.putString(PREFERENCE_KEY_USER_TOKEN, token).apply()
        }
    var connected: Boolean
        get() = sharedPreferences.getBoolean(PREFERENCE_KEY_CONNECTED, false)
        set(tf) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(PREFERENCE_KEY_CONNECTED, tf).apply()
        }

    fun clearAll() {
        val editor = sharedPreferences.edit()
        editor.clear().apply()
    }

    var notifications: Boolean
        get() = sharedPreferences.getBoolean(PREFERENCE_KEY_NOTIFICATIONS, true)
        set(notifications) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(PREFERENCE_KEY_NOTIFICATIONS, notifications).apply()
        }
    var notificationsShowPreviews: Boolean
        get() = sharedPreferences.getBoolean(PREFERENCE_KEY_NOTIFICATIONS_SHOW_PREVIEWS, true)
        set(notificationsShowPreviews) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(PREFERENCE_KEY_NOTIFICATIONS_SHOW_PREVIEWS, notificationsShowPreviews)
                .apply()
        }
    var notificationsDoNotDisturb: Boolean
        get() = sharedPreferences.getBoolean(PREFERENCE_KEY_NOTIFICATIONS_DO_NOT_DISTURB, false)
        set(notificationsDoNotDisturb) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(
                PREFERENCE_KEY_NOTIFICATIONS_DO_NOT_DISTURB,
                notificationsDoNotDisturb
            ).apply()
        }
    var notificationsDoNotDisturbFrom: String?
        get() = sharedPreferences.getString(PREFERENCE_KEY_NOTIFICATIONS_DO_NOT_DISTURB_FROM, "")
        set(notificationsDoNotDisturbFrom) {
            val editor = sharedPreferences.edit()
            editor.putString(
                PREFERENCE_KEY_NOTIFICATIONS_DO_NOT_DISTURB_FROM,
                notificationsDoNotDisturbFrom
            ).apply()
        }
    var notificationsDoNotDisturbTo: String?
        get() = sharedPreferences.getString(PREFERENCE_KEY_NOTIFICATIONS_DO_NOT_DISTURB_TO, "")
        set(notificationsDoNotDisturbTo) {
            val editor = sharedPreferences.edit()
            editor.putString(
                PREFERENCE_KEY_NOTIFICATIONS_DO_NOT_DISTURB_TO,
                notificationsDoNotDisturbTo
            ).apply()
        }
    var groupChannelDistinct: Boolean
        get() = sharedPreferences.getBoolean(PREFERENCE_KEY_GROUP_CHANNEL_DISTINCT, true)
        set(channelDistinct) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(PREFERENCE_KEY_GROUP_CHANNEL_DISTINCT, channelDistinct).apply()
        }
}