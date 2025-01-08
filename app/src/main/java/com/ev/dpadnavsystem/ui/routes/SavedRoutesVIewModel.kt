package com.ev.dpadnavsystem.ui.routes

import androidx.lifecycle.ViewModel
import com.ev.dpadnavsystem.data.model.ChargersModel
import com.ev.dpadnavsystem.data.model.FavoritesModel
import com.ev.dpadnavsystem.data.repo.ChargersRepository
import com.ev.dpadnavsystem.data.repo.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SavedRoutesVIewModel @Inject constructor(
    private val favoriteRepo: FavoritesRepository,
    private val chargersRepo: ChargersRepository
) : ViewModel() {

    private val _favoritesList = MutableStateFlow<MutableList<FavoritesModel>>(mutableListOf())
    val favorites = _favoritesList.asStateFlow()

    private val _chargersList = MutableStateFlow<MutableList<ChargersModel>>(mutableListOf())
    val nearByChargers = _chargersList.asStateFlow()

    init {
        getFavorites()
        getNearByChargerLocation()
    }

    private fun getFavorites() {
        _favoritesList.value = favoriteRepo.getFavoritesList()
    }

    private fun getNearByChargerLocation() {
        _chargersList.value = chargersRepo.getNearByChargers()
    }
}