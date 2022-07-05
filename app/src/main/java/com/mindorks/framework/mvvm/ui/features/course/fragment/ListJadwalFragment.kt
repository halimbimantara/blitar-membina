package com.mindorks.framework.mvvm.ui.features.course.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.framework.mvvm.databinding.ContentAppliedCourseBinding
import com.mindorks.framework.mvvm.ui.features.course.adapter.AdapterKurikulum
import com.mindorks.framework.mvvm.ui.features.course.model.KurikulumModel

class ListJadwalFragment : Fragment() {
    private var _binding: ContentAppliedCourseBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ContentAppliedCourseBinding.inflate(inflater, container, false)
        val mAdapter = AdapterKurikulum(KurikulumModel.listItemJadwal)
        binding.rvList.adapter = mAdapter
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}