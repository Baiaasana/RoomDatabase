package com.example.roomdb.data.repository

import com.example.roomdb.dao.UserDao
import com.example.roomdb.data.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao){

    val readAllData: Flow<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }
    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }
}