package com.ev.dpadnavsystem.ui.routes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ev.dpadnavsystem.R
import com.ev.dpadnavsystem.adapter.ChargersAdapter
import com.ev.dpadnavsystem.adapter.FavoritesAdapter
import com.ev.dpadnavsystem.databinding.SavedRoutesFragmentBinding
import com.ev.dpadnavsystem.utils.collectFlowOnLifeCycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedRoutesFragment : Fragment() {

    private lateinit var binding: SavedRoutesFragmentBinding

    private val viewModel by viewModels<SavedRoutesVIewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.saved_routes_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        collectFlowOnLifeCycle(viewModel.favorites) {
            binding.recyclerFavorites.adapter = FavoritesAdapter(it)
        }

        collectFlowOnLifeCycle(viewModel.nearByChargers) {
            binding.recyclerChargers.adapter = ChargersAdapter(it)
        }
    }
}