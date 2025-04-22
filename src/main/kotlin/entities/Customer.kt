package com.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Customers : IntIdTable("customers") {
    val username = varchar("username" , 50)
    val email = varchar("email" ,100).uniqueIndex()
    val passwordHash = varchar("password_hash" , 255)
    val type = varchar("type" , 50).default("customer")
    val address = varchar("address", 200).nullable()
    val phone = varchar("phone" ,15).nullable()
    val profilePicture = varchar("profile_picture" , 50).nullable()
}

class CustomerEntity(id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CustomerEntity>(Customers)
    var userName by Customers.username
    var email by Customers.email
    var passwordHash by Customers.passwordHash
    var type by Customers.type
    var address by Customers.address
    var phone by Customers.phone
    var profilePicture by Customers.profilePicture
}