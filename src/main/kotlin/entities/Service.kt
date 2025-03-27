package com.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Services : IntIdTable("services") {
    val name = varchar("name" , 100)
    val description = text("description")
    val isActive = bool("is_active").default(true)
}

class ServiceEntity(id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ServiceEntity>(Services)
    var name by Services.name
    var description by Services.description
    var isActive by Services.isActive
}