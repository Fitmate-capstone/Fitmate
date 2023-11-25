package com.tegar.fitmate.data.model

import androidx.annotation.DrawableRes


data class Exercise(
    val id : Long,
    val name : String,
    val rating : Int,
    val level : Int,
    val calEstimation : Int,
    val requiredEquiment : Boolean,
    val explain : String,
    val step : Array<String>,
    val category : Category,
    val muscle : Muscle,
    @DrawableRes val photo : Int,
)