package com.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Professionals : IntIdTable("professionals") {
    val userName = varchar("username" , 50)
    val email = varchar("email" , 100).uniqueIndex()
    val passwordHash = varchar("password_hash" , 255)
    val type = varchar("type" , 50).default("professional")
    val experience = integer("experience")
    val documents = binary("documents")
    val isApproved = bool("is_approved").default(false)
}


class ProfessionalEntity(id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProfessionalEntity>(Professionals)
    var username by Professionals.userName
    var email by Professionals.email
    var passwordHash by Professionals.passwordHash
    var type by Professionals.type
    var experience by Professionals.experience
    var documents by Professionals.documents
    var isApproved by Professionals.isApproved
}