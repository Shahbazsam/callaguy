package com.entities

import org.h2.engine.User
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable("users") {
    val username = varchar("username" , 50).uniqueIndex()
    val email = varchar("email" ,100).uniqueIndex()
    val passwordHash = varchar("password_hash" , 255)
    val type = varchar("type" , 50).default("customer")
    val address = varchar("address", 200).nullable()
    val phone = varchar("phone" ,15).nullable()
    val experience = integer("experience").nullable()
    val documents = text("documents").nullable()
}

class UserEntity(id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)
    var userName by Users.username
    var email by Users.email
    var passwordHash by Users.passwordHash
    var type by Users.type
    var address by Users.address
    var phone by Users.phone
    var experience by Users.experience
    var documents by Users.documents
}