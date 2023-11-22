package com.tegar.fitmate.ui.model

import androidx.annotation.DrawableRes
import com.tegar.fitmate.ui.screens.routelist.ScreenRoute

data class NavigationItem(
    val title: String,
    @DrawableRes val icon: Int,
    val screen: ScreenRoute
)
