package com.csahmed2020.demo.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.csahmed2020.demo.sqldelight.myDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(myDatabase.Schema, DB_NAME)
    }
}