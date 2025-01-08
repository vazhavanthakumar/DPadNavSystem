package com.ev.dpadnavsystem.data.repo

import com.ev.dpadnavsystem.data.model.ChargersModel
import com.ev.dpadnavsystem.data.model.FavoritesModel
import javax.inject.Inject

class ChargersRepository @Inject constructor() {

    fun getNearByChargers(): MutableList<ChargersModel> {
        return mutableListOf(
            ChargersModel("Whitefield"),
            ChargersModel("Kr puram"),
            ChargersModel("SMVB"),
            ChargersModel("Koramangala"),
        )
    }

}