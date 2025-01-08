package com.ev.dpadnavsystem.ui.bluetooth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ev.dpadnavsystem.R
import com.ev.dpadnavsystem.databinding.BluetoothFragmentBinding

class BluetoothFragment: Fragment() {

    private lateinit var binding: BluetoothFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bluetooth_fragment, container, false)
        return  binding.root
    }
}