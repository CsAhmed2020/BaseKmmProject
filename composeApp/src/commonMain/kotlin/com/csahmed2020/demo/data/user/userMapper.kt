package com.csahmed2020.demo.data.user

import com.csahmed2020.demo.domain.user.User
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import database.User as UserEntity

fun UserEntity.toUser(): User {
    return User(
        id = id,
        title = title,
        address = address ?: "",
        age = age ?: 0
    )
}