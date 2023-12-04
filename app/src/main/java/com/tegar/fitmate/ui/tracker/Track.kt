package com.tegar.fitmate.ui.tracker

import com.tegar.fitmate.ui.model.Person


data class Track(
    val person: Person,
    val lastTimestamp: Long
)
