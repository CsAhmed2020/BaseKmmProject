package com.csahmed2020.demo.data.user

import com.csahmed2020.demo.domain.user.User
import com.csahmed2020.demo.domain.user.UserDataSource
import com.csahmed2020.demo.sqldelight.MyDatabase



class SqlDelightUserDataSource(db: MyDatabase): UserDataSource {

    private val queries = db.userQueries

    override suspend fun insertUser(user: User) {
        queries.insert(
            id = user.id,
            title = user.title,
            age = user.age,
            address = user.address,
        )
    }

    override suspend fun getUserById(id: Long): User? {
        return queries
            .getById(id)
            .executeAsOneOrNull()
            ?.toUser()
    }

    override suspend fun getAllUsers(): List<User> {
        return queries
            .getAllUsers()
            .executeAsList()
            .map { it.toUser() }
    }

    override suspend fun deleteUserById(id: Long) {
        queries.deleteWithId(id)
    }
}
