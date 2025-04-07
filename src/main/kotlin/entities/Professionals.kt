package com.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object Professionals : IntIdTable("professionals") {
    val username = varchar("username" , 50)
    val email = varchar("email" , 100).uniqueIndex()
    val passwordHash = varchar("password_hash" , 255)
    val type = varchar("type" , 50).default("professional")
    val experience = integer("experience")
    val documents = binary("documents")
    val isApproved = bool("is_approved").default(false)
}


class ProfessionalEntity(id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProfessionalEntity>(Professionals)
    var username by Professionals.username
    var email by Professionals.email
    var passwordHash by Professionals.passwordHash
    var type by Professionals.type
    var experience by Professionals.experience
    var documents by Professionals.documents
    var isApproved by Professionals.isApproved

    var services by ServiceEntity via ProfessionalServices
}

object ProfessionalServices : Table("professional_services") {
    val professional = reference("professional_id" , Professionals)
    val service = reference("service_id" , Services)

    override val primaryKey = PrimaryKey(professional , service)
}