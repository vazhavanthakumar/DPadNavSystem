package com.ev.dpadnavsystem.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _submenuSelectionState = MutableStateFlow(0)
    val subMenuSelectionState = _submenuSelectionState.asStateFlow()

    fun updateSubMenuSelectionState(value: Int) {
        _submenuSelectionState.value = value
    }

    private val _selectedSubMenu = MutableStateFlow(0)
    val selectedSubMenu = _selectedSubMenu.asStateFlow()

    fun updateSelectedSubMenuID(value: Int) {
        _selectedSubMenu.value = value
    }
}