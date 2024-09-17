package com.csahmed2020.demo.data.di

import com.csahmed2020.demo.data.local.DatabaseDriverFactory
import com.csahmed2020.demo.data.user.SqlDelightUserDataSource
import com.csahmed2020.demo.domain.user.UserDataSource
import com.csahmed2020.demo.sqldelight.myDatabase

class DatabaseModule {

    private val factory by lazy { DatabaseDriverFactory() }
    val userDataSource: UserDataSource by lazy {
        SqlDelightUserDataSource(myDatabase(factory.createDriver()))
    }
}