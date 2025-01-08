package com.ev.dpadnavsystem.data.repo

import com.ev.dpadnavsystem.data.model.FavoritesModel
import javax.inject.Inject

class FavoritesRepository @Inject constructor() {

    fun getFavoritesList(): MutableList<FavoritesModel> {
        return mutableListOf(
            FavoritesModel("House"),
            FavoritesModel("Hostel"),
            FavoritesModel("Market"),
            FavoritesModel("Hotel"),
        )
    }

}