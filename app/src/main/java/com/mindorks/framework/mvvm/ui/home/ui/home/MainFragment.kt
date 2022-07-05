package com.mindorks.framework.mvvm.ui.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mindorks.framework.mvvm.databinding.ContentHomeBinding
import com.mindorks.framework.mvvm.ui.home.adapter.MenuAdapter
import com.mindorks.framework.mvvm.ui.home.models.MenuGrid
import com.mindorks.framework.mvvm.utils.PreferenceUtils

class MainFragment : Fragment() {
    private var _binding: ContentHomeBinding? = null
    private val binding get() = _binding!!
    var mListItemMenu: ArrayList<MenuGrid>? = java.util.ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ContentHomeBinding.inflate(inflater, container, false)
        val accessTipe = PreferenceUtils.userRole
        when (accessTipe) {
            PreferenceUtils.EXTRA_TYPE_ROLE_PELAMAR -> {
                mListItemMenu?.addAll(MenuGrid.listMenu)
            }
            PreferenceUtils.EXTRA_TYPE_ROLE_PEMBINA -> {
                mListItemMenu?.addAll(MenuGrid.listMenuPembina)
            }
        }
        val adapter = MenuAdapter(mListItemMenu!!, requireContext())
        binding.menuGrid.adapter = adapter
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mListItemMenu?.clear()
    }
}