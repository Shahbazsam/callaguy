package com.repository

import com.entities.Admin
import com.entities.AdminEntity
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

interface AdminRepository {
    suspend fun findByEmail(email : String) : AdminEntity?
    suspend fun findById(id : Int): AdminEntity?
    suspend fun createAdmin(
        userName : String ,
        email : String ,
        password : String ,
    ) : Boolean
}

class AdminRepositoryImpl : AdminRepository {
    override suspend fun findByEmail(email: String): AdminEntity? {
        return transaction {
            AdminEntity.find(Admin.email eq email).firstOrNull()
        }
    }

    override suspend fun findById(id: Int): AdminEntity? {
        return transaction {
            AdminEntity.findById(id)
        }
    }

    override suspend fun createAdmin(
        userName: String,
        email: String,
        password: String
    ): Boolean {
        try {
            transaction {
                AdminEntity.new {
                    this.userName = userName
                    this.email = email
                    this.passwordHash = password
                }
            }
            return true
        }catch (e : Exception) {
            return false
        }
    }
}