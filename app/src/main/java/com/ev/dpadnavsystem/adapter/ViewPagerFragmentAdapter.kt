package com.ev.dpadnavsystem.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ev.dpadnavsystem.ui.bluetooth.BluetoothFragment
import com.ev.dpadnavsystem.ui.controls.ControlsFragment
import com.ev.dpadnavsystem.ui.routes.SavedRoutesFragment

class ViewPagerFragmentAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = listOf(
        SavedRoutesFragment(),
        BluetoothFragment(),
        ControlsFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
