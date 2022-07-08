/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package com.mindorks.framework.mvvm.ui.account.login.register

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION_CODES.S
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.core.data.model.others.AppDataConstants.Register.user
import com.mindorks.framework.mvvm.core.data.remote.NetworkErrorHandlingTag
import com.mindorks.framework.mvvm.core.ui.common.extensions.etToString
import com.mindorks.framework.mvvm.core.utils.AppConstants
import com.mindorks.framework.mvvm.core.utils.AppConstants.USER_GENDER_FEMALE
import com.mindorks.framework.mvvm.core.utils.AppConstants.USER_GENDER_MALE
import com.mindorks.framework.mvvm.core.utils.CommonUtils
import com.mindorks.framework.mvvm.core.utils.DateUtils
import com.mindorks.framework.mvvm.core.utils.rx.observe
import com.mindorks.framework.mvvm.databinding.ActivityRegisterBinding
import com.mindorks.framework.mvvm.di.component.ActivityComponent
import com.mindorks.framework.mvvm.ui.base.BaseActivity
import com.mindorks.framework.mvvm.ui.home.HomeActivityMain
import org.hawlastudio.binaahli.utils.ext.gone
import org.hawlastudio.binaahli.utils.ext.visible
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : BaseActivity<ActivityRegisterBinding?, RegisterViewModel?>() {
    private var tglLahir: String = ""
    private var gender: String = ""//
    private lateinit var mBinding: ActivityRegisterBinding
    val listJenkel = arrayOf("--Pilih jenis kelamin--", "Laki - Laki", "Perempuan")

    var calendar = Calendar.getInstance()
    fun openMainActivity() {
        val intent: Intent = HomeActivityMain.newIntent(this@RegisterActivity)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = viewDataBinding!!
        intUi()
        actionUi()
    }

    private fun intUi() {
        val items = listOf(*listJenkel)
        val adapter = CompletionAdapter(
            this, R.layout.item_drop_downl,
            items as MutableList<String>
        )
        mBinding.EtJenkel.setAdapter(adapter)
    }

    private fun actionUi() {
        val startdateListener =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel(mBinding.EtTtl)
            }

        mBinding.EtTtl.setOnClickListener { v: View? ->
            val mdialog = DatePickerDialog(
                this@RegisterActivity,
                startdateListener,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )
            mdialog.show()
        }
        mBinding.EtJenkel.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView: AdapterView<*>?, view: View?, i: Int, l: Long ->
                gender = if (listJenkel[i].contains("Laki")) {
                    USER_GENDER_MALE
                } else if (listJenkel[i].contains("Perempuan")) {
                    USER_GENDER_FEMALE
                } else {
                    listJenkel[i]
                }
            }

        mBinding.BtnApply.setOnClickListener {
            formValidation()
        }
    }

    private fun updateLabel(textView: TextView) {
        val myFormat = AppConstants.DATE_FORMAT_MDY_KOMA //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        textView.text = sdf.format(calendar.time)
        tglLahir = DateUtils.formatDateto(
            sdf.format(calendar.time),
            AppConstants.DATE_FORMAT_DEFAULT
        )
    }

    private fun formValidation() {
        val firstName = mBinding.EtNamaDepan.etToString()
        val lastName = mBinding.EtNamaBelakang.etToString()
        val email = mBinding.EtEmail.etToString()
        val pwd = mBinding.EtPassword.etToString()
        val uname = mBinding.EtUsername.etToString()
        val ttl = mBinding.EtTtl.etToString()

        if (firstName.isEmpty()) {
            mBinding.EtNamaDepan.error = getString(R.string.info_field_canempty)
        } else if (lastName.isEmpty()) {
            mBinding.EtNamaBelakang.error = getString(R.string.info_field_canempty)
        } else if (email.isEmpty()) {
            mBinding.EtEmail.error = getString(R.string.info_field_canempty)
        } else if (pwd.isEmpty()) {
            mBinding.EtPassword.error = getString(R.string.info_field_canempty)
        } else if (uname.isEmpty()) {
            mBinding.EtUsername.error = getString(R.string.info_field_canempty)
        } else if (ttl.isEmpty()) {
            mBinding.EtTtl.error = getString(R.string.info_field_canempty)
        } else if (gender.contains("Pilih")) {
            mBinding.EtJenkel.error = getString(R.string.info_field_canempty)
        } else {
            showLoadingProgress(true)
            mViewModel!!.postRegister(firstName, lastName, gender, user, email, uname, pwd)
        }
    }

    private fun showLoadingProgress(show: Boolean) {
        if (show) {
            mBinding.BtnApply.gone()
            mBinding.CLoading.visible()
        } else {
            mBinding.BtnApply.visible()
            mBinding.CLoading.gone()
        }
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }

    override val bindingVariable: Int
        get() = 0

    override val layoutId: Int
        get() = R.layout.activity_register

    override fun observeChange() {
        observe(mViewModel!!.successRegister, ::isSuccess)
        observe(mViewModel!!.errorData, ::handlingError)
    }

    private fun handlingError(errorResponse: NetworkErrorHandlingTag) {
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

    private fun isSuccess(successRegister: Boolean) {
        if (successRegister) {
            showMessage(getString(R.string.alert_success_register))
            showLoadingProgress(false)
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 3000)
        }
    }

}