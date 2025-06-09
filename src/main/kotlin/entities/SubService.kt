package com.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object SubServices : IntIdTable("sub_services") {
    val serviceId = reference("service_id", Services)
    val name = varchar("name" , 100)
    val basePrice = decimal("base_price" , 10 , 2)
    val visitCharge = decimal("visit_Charge" , 10 ,2)
    val isSubscription = bool("is_subscription").default(false)
    val subscriptionDuration = integer("subscription_duration").nullable()
    val imageUrl = varchar("image_url" , 100).nullable()

}

class SubServiceEntity(id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SubServiceEntity>(SubServices)
    var serviceId by SubServices.serviceId
    var name by SubServices.name
    var basePrice by SubServices.basePrice
    var visitCharge by SubServices.visitCharge
    var isSubscription by SubServices.isSubscription
    var subscriptionDuration by SubServices.subscriptionDuration
    var imageUrl by SubServices.imageUrl
}