package com.mindorks.framework.mvvm.ui.features.course.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.core.ui.common.extensions.etToString
import com.mindorks.framework.mvvm.databinding.ActicityCourseDetailBinding
import com.mindorks.framework.mvvm.di.component.ActivityComponent
import com.mindorks.framework.mvvm.ui.base.BaseActivity
import com.mindorks.framework.mvvm.ui.features.course.viewmodels.DetailCourseViewModel
import com.mindorks.framework.mvvm.ui.features.profile.ProfileUser
import com.mindorks.framework.mvvm.utils.PreferenceUtils
import com.mindorks.framework.mvvm.utils.ext.gone
import com.mindorks.framework.mvvm.utils.ext.loadUrlPicture
import com.mindorks.framework.mvvm.utils.ext.visible

class DetailCourse : BaseActivity<ActicityCourseDetailBinding, DetailCourseViewModel>() {
    private lateinit var binding: ActicityCourseDetailBinding

    private val titleCourse by lazy {
        intent.getStringExtra(EXTRA_TITLE)
    }

    private val idLowongan by lazy {
        intent.getIntExtra(EXTRA_ID_LOWONGAN, 0)
    }

    private val namaUsaha by lazy {
        intent.getStringExtra(EXTRA_TITLE_TEMPAT_USAHA)
    }

    private val categoryName by lazy {
        intent.getStringExtra(EXTRA_TITLE_KATEGORI)
    }

    private val alamat by lazy {
        intent.getStringExtra(EXTRA_TITLE_ALAMAT)
    }

    private val materiMagang by lazy {
        intent.getStringExtra(EXTRA_TITLE_MATERI)
    }

    private val quotaMagang by lazy {
        intent.getIntExtra(EXTRA_QUOTA, 0)
    }

    private val totalPelamar by lazy {
        intent.getIntExtra(EXTRA_QUOTA, 0)
    }
    private val jadwalMagang by lazy {
        intent.getStringExtra(EXTRA_JADWAL)
    }

    private val nmPembina by lazy {
        intent.getStringExtra(EXTRA_NAMA_PEMBINA)
    }


    private val idPembina by lazy {
        intent.getIntExtra(EXTRA_ID_PEMBINA, 0)
    }

    private val coverLoker by lazy {
        intent.getStringExtra(EXTRA_COVER_PICT)
    }

    private val noTlp by lazy {
        intent.getStringExtra(EXTRA_NO_TELEPHONE)
    }


    /**
     * role
     * user
     * pembina
     */
    private val accessTipe by lazy {
        intent.getStringExtra(EXTRA_TIPE)
    }

    /**
     * pembina
     * - tampil user pelamar
     * - lihat laporan
     */
    override val bindingVariable: Int
        get() = 0
    override val layoutId: Int
        get() = R.layout.acticity_course_detail

    override fun observeChange() {

    }

    override fun performDependencyInjection(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_IS_FROM_NOTIFY = "extra_is_from_notify"
        const val EXTRA_TITLE_TEMPAT_USAHA = "extra_title_tempat_usaha"
        const val EXTRA_TITLE_KATEGORI = "extra_title_kategori"
        const val EXTRA_TITLE_ALAMAT = "extra_alamat"
        const val EXTRA_TITLE_MATERI = "extra_materi"
        const val EXTRA_QUOTA = "extra_quota"
        const val EXTRA_TOTAL_PELAMAR = "extra_total_pelamar"
        const val EXTRA_COVER_PICT = "extra_cover_pict"
        const val EXTRA_NAMA_PEMBINA = "extra_nama_pembina"
        const val EXTRA_ID_LOWONGAN = "extra_id_lowongan"
        const val EXTRA_ID_PEMBINA = "extra_id_pembina"
        const val EXTRA_NO_TELEPHONE = "extra_no_telephone"
        const val EXTRA_JADWAL = "extra_jadwal"

        /**
         * tipe student,pembina
         */
        const val EXTRA_TIPE = "EXTRA_TIPE"

        fun newIntent(
            context: Context?,
            mTitle: String,
            id: Int? = null,
            tUsaha: String? = null,
            titleCategory: String,
            alamat: String? = null,
            noTelephone: String? = null,
            materi: String? = null,
            jadwal: String? = null,
            quota: Int? = null,
            totalPelamar: Int? = null,
            idPembina: Int? = null,
            nmPembina: String? = null,
            role: String? = null,
            coverPick: String? = null,
            isNotify: Boolean? = null
        ): Intent {
            return Intent(context, DetailCourse::class.java).apply {
                putExtra(EXTRA_TITLE, mTitle)
                putExtra(EXTRA_ID_LOWONGAN, id)
                putExtra(EXTRA_TIPE, role)
                putExtra(EXTRA_IS_FROM_NOTIFY, isNotify)
                putExtra(EXTRA_TITLE_TEMPAT_USAHA, tUsaha)
                putExtra(EXTRA_TITLE_KATEGORI, titleCategory)
                putExtra(EXTRA_NAMA_PEMBINA, nmPembina)
                putExtra(EXTRA_ID_PEMBINA, idPembina)
                putExtra(EXTRA_TITLE_ALAMAT, alamat)
                putExtra(EXTRA_NO_TELEPHONE, noTelephone)
                putExtra(EXTRA_TITLE_MATERI, materi)
                putExtra(EXTRA_JADWAL, jadwal)
                putExtra(EXTRA_QUOTA, quota)
                putExtra(EXTRA_TOTAL_PELAMAR, totalPelamar)
                putExtra(EXTRA_COVER_PICT, coverPick)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        title = titleCourse
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.textViewPembinaDetail.setOnClickListener {
            startActivity(ProfileUser.newIntent(this))
        }
        when (accessTipe) {
            PreferenceUtils.EXTRA_TYPE_ROLE_PELAMAR -> {
                binding.BtnApply.visible()
                actionApply()
            }
            PreferenceUtils.EXTRA_TYPE_ROLE_PEMBINA -> {
                if (idPembina == mViewModel?.dataManager?.currentUserId?.toInt()) {
                    //show  user list applied
                    binding.BtnListPelamar.visible()
                    binding.BtnListPelamar.setOnClickListener {
                        startActivity(ListStudentApplyCourse.newIntent(this, idLowongan))
                    }
                }
                binding.BtnApply.gone()
            }
        }
        initData()
    }

    private fun initData() {
        if (!namaUsaha.isNullOrEmpty()) {
            binding.tvNamaPembina.text = nmPembina
            binding.tvAlamatLoker.text = alamat
            binding.textViewKuota.text = "$quotaMagang orang"
            binding.textViewTitle.text = titleCourse
            binding.textViewSubTitle.text = namaUsaha
            binding.TvJadwal.text = jadwalMagang
            binding.TvMateri.text = materiMagang
            binding.imvContent.loadUrlPicture(coverLoker!!)
            binding.TvNoTelephone.text = noTlp
        }


    }

    private fun actionApply() {
        actionDialogStudent()
    }

    private fun actionDialogStudent() {
        binding.BtnApply.setOnClickListener {
            MaterialDialog(this).show {
                message(text = "Apakah anda yakin mengajukan lamaran ini ?")
                val mView = customView(R.layout.dialog_content_enter_reason_apply)
                val etReasonApply = mView.getCustomView().findViewById<EditText>(R.id.EtEntryReason)
                positiveButton(R.string.submit) { dialog ->
                    // Do something
                    mViewModel!!.applyLowongan(idLowongan, etReasonApply.etToString())
                }
                negativeButton(R.string.batal) { dialog ->
                    // Do something
                    dismiss()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }
}