package com.repository

import com.entities.UserEntity
import com.entities.Users
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

interface UserRepository {
    suspend fun findByEmail(email : String) : UserEntity?
    suspend fun findById( userId : Int) : UserEntity?
    suspend fun createUser(
        userName : String,
        email: String,
        password : String,
        type : String,
        address : String,
        phone : String
    ): Boolean
}

class UserRepositoryImpl : UserRepository {
    override suspend fun findByEmail(email: String): UserEntity? {
        return transaction {
            UserEntity.find(Users.email eq email ).firstOrNull()
        }
    }

    override suspend fun findById(userId: Int): UserEntity? {
        return transaction {
            UserEntity.findById(userId)
        }
    }

    override suspend fun createUser(
        userName: String,
        email: String,
        password: String,
        type: String,
        address: String,
        phone: String
    ) : Boolean {
        try {
            transaction {
                UserEntity.new {
                    this.userName = userName
                    this.email = email
                    this.passwordHash = password
                    this.type = type
                    this.address = address
                    this.phone = phone
                }
            }
            return true
        } catch (e : Exception) {
           return  false
        }
    }
}