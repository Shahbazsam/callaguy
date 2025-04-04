package com.repository

import com.entities.CustomerEntity
import com.entities.Customers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

interface CustomerRepository {
    suspend fun findByEmail(email : String) : CustomerEntity?
    suspend fun findById( userId : Int) : CustomerEntity?
    suspend fun createUser(
        userName : String,
        email: String,
        password : String,
        type : String,
        address : String,
        phone : String
    ): Boolean
}

class CustomerRepositoryImpl : CustomerRepository {
    override suspend fun findByEmail(email: String): CustomerEntity? {
        return transaction {
            CustomerEntity.find(Customers.email eq email ).firstOrNull()
        }
    }

    override suspend fun findById(userId: Int): CustomerEntity? {
        return transaction {
            CustomerEntity.findById(userId)
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
                CustomerEntity.new {
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