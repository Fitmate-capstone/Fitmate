package com.tegar.fitmate.ui.state

data class InteractiveUiState(
    val isTutorialScreen : Boolean = true,
    val score : Int = 0,
    val counter : Int = 0,
    val isFinish : Boolean = false,
    val findingObject : Boolean = true,
)