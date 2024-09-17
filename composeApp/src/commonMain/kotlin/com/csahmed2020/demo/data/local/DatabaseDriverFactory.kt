package com.csahmed2020.demo.data.local

import app.cash.sqldelight.db.SqlDriver


expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}
const val DB_NAME = "myDatabase.db"