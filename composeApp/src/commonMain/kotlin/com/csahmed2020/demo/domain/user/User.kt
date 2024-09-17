package com.csahmed2020.demo.domain.user

import kotlinx.datetime.LocalDateTime

data class User(
    val id: Long?,
    val title: String,
    val age: Long,
    val address: String
)