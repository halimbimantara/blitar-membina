package com.mindorks.framework.mvvm.ui.home.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.framework.mvvm.databinding.FragmentDashboardBinding
import com.mindorks.framework.mvvm.utils.PreferenceUtils
import org.hawlastudio.binaahli.ui.menu.dashboard.DashboardViewModel
import com.mindorks.framework.mvvm.ui.home.adapter.AdapterOffline
import com.mindorks.framework.mvvm.ui.home.models.CourseModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mAdapter = AdapterOffline(CourseModel.listGuide, PreferenceUtils.userRole)
        binding.rvCourse.adapter = mAdapter
        binding.rvCourse.layoutManager = LinearLayoutManager(requireContext())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}