package com.ev.dpadnavsystem.ui.submenu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.ev.dpadnavsystem.R
import com.ev.dpadnavsystem.adapter.ViewPagerFragmentAdapter
import com.ev.dpadnavsystem.databinding.SubmenuFragmentBinding
import com.ev.dpadnavsystem.ui.home.HomeViewModel
import com.ev.dpadnavsystem.utils.MenuTypes
import com.ev.dpadnavsystem.utils.collectFlowOnLifeCycle

class SubMenuScreenFragment : Fragment() {

    private lateinit var binding: SubmenuFragmentBinding

    private val homeViewModel by activityViewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.submenu_fragment, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        subscribeObservers()
        arguments?.let {
            val data = it.getInt(MenuTypes.TYPE_MENU)
            Log.e("TAG", "onViewCreated: $data", )
            binding.viewPager.setCurrentItem(data, true)
        }
    }

    private fun initViewPager() {
        binding.viewPager.adapter = ViewPagerFragmentAdapter(requireActivity())
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e("TAG", "onPageSelected: $position")
                homeViewModel.updateSubMenuSelectionState(position)
            }
        })
    }

    private fun subscribeObservers() {
        collectFlowOnLifeCycle(homeViewModel.selectedSubMenu) {
            binding.viewPager.setCurrentItem(it, true)
        }
    }
}