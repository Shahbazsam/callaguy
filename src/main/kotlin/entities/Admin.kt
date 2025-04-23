package com.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Admin : IntIdTable("admin"){
    val userName = varchar("name" , 20)
    val email = varchar("email" , 50).uniqueIndex()
    val passwordHash = varchar("password_hash" , 255)
    val type = varchar("type" , 20).default("admin")
}

class AdminEntity(id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AdminEntity>(Admin)
    var userName by Admin.userName
    var email by Admin.email
    var passwordHash by Admin.passwordHash
    var type by Admin.type
}