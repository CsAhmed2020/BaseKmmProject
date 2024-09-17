package com.csahmed2020.demo.domain.user

interface UserDataSource {
    suspend fun insertUser(user: User)
    suspend fun getUserById(id: Long): User?
    suspend fun getAllUsers(): List<User>
    suspend fun deleteUserById(id: Long)
}