package com

import com.entities.ServiceEntity
import com.entities.Services
import com.entities.SubServiceEntity
import com.entities.SubServices
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal

fun seedServicesAndSubServices() {
    transaction {
        if (ServiceEntity.all().empty()) {
            val cleaning = ServiceEntity.new {
                name = "Cleaning"
                description = "Professional home and office cleaning."
                imageUrl = null
            }

            val plumbing = ServiceEntity.new {
                name = "Plumbing"
                description = "Fix leaks, install pipes and fittings."
                imageUrl = null
            }

            val electrical = ServiceEntity.new {
                name = "Electrical"
                description = "Wiring, lights, and appliances repair."
                imageUrl = null
            }

            val applianceRepair = ServiceEntity.new {
                name = "Home Appliance Repair"
                description = "Repair washing machines, fridges, etc."
                imageUrl = null
            }

            val pestControl = ServiceEntity.new {
                name = "Pest Control"
                description = "Get rid of insects and rodents safely."
                imageUrl = null
            }

            val acServices = ServiceEntity.new {
                name = "AC Services"
                description = "AC installation, maintenance, and repair."
                imageUrl = null
            }

            val carpentry = ServiceEntity.new {
                name = "Carpentry"
                description = "Furniture assembly and woodwork repair."
                imageUrl = null
            }

            val painting = ServiceEntity.new {
                name = "Painting"
                description = "Interior and exterior painting services."
                imageUrl = null
            }

            val sanitization = ServiceEntity.new {
                name = "Home Sanitization"
                description = "Disinfection and sanitization for safety."
                imageUrl = null
            }

            val gardening = ServiceEntity.new {
                name = "Gardening & Landscaping"
                description = "Lawn mowing, hedge trimming, and more."
                imageUrl = null
            }

            // Cleaning Subservices
            SubServiceEntity.new {
                serviceId = cleaning.id
                name = "Basic Home Cleaning"
                basePrice = BigDecimal("2000.00")
                visitCharge = BigDecimal("500.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = cleaning.id
                name = "Deep Cleaning"
                basePrice = BigDecimal("5000.00")
                visitCharge = BigDecimal("1000.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = cleaning.id
                name = "Monthly Cleaning Plan"
                basePrice = BigDecimal("10000.00")
                visitCharge = BigDecimal("0.00")
                isSubscription = true
                subscriptionDuration = 30
                imageUrl = null
            }

            // Plumbing Subservices
            SubServiceEntity.new {
                serviceId = plumbing.id
                name = "Tap Leak Fix"
                basePrice = BigDecimal("1500.00")
                visitCharge = BigDecimal("500.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = plumbing.id
                name = "Water Tank Installation"
                basePrice = BigDecimal("8000.00")
                visitCharge = BigDecimal("2000.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = plumbing.id
                name = "Plumbing Checkup Plan"
                basePrice = BigDecimal("4000.00")
                visitCharge = BigDecimal("0.00")
                isSubscription = true
                subscriptionDuration = 90
                imageUrl = null
            }

            // Electrical Subservices
            SubServiceEntity.new {
                serviceId = electrical.id
                name = "Light Fitting"
                basePrice = BigDecimal("2000.00")
                visitCharge = BigDecimal("500.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = electrical.id
                name = "Wiring Repair"
                basePrice = BigDecimal("6000.00")
                visitCharge = BigDecimal("1000.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = electrical.id
                name = "Electrical Safety Plan"
                basePrice = BigDecimal("10000.00")
                visitCharge = BigDecimal("0.00")
                isSubscription = true
                subscriptionDuration = 180
                imageUrl = null
            }

            // Appliance Repair Subservices
            SubServiceEntity.new {
                serviceId = applianceRepair.id
                name = "Refrigerator Repair"
                basePrice = BigDecimal("4500.00")
                visitCharge = BigDecimal("1000.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = applianceRepair.id
                name = "Washing Machine Repair"
                basePrice = BigDecimal("5000.00")
                visitCharge = BigDecimal("1000.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = applianceRepair.id
                name = "Appliance Maintenance Plan"
                basePrice = BigDecimal("12000.00")
                visitCharge = BigDecimal("0.00")
                isSubscription = true
                subscriptionDuration = 90
                imageUrl = null
            }

            // Pest Control Subservices
            SubServiceEntity.new {
                serviceId = pestControl.id
                name = "Cockroach Treatment"
                basePrice = BigDecimal("3000.00")
                visitCharge = BigDecimal("500.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = pestControl.id
                name = "Rodent Control"
                basePrice = BigDecimal("4000.00")
                visitCharge = BigDecimal("500.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = pestControl.id
                name = "Quarterly Pest Plan"
                basePrice = BigDecimal("9000.00")
                visitCharge = BigDecimal("0.00")
                isSubscription = true
                subscriptionDuration = 90
                imageUrl = null
            }

            // AC Services Subservices
            SubServiceEntity.new {
                serviceId = acServices.id
                name = "AC Gas Refill"
                basePrice = BigDecimal("6000.00")
                visitCharge = BigDecimal("1000.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = acServices.id
                name = "AC Installation"
                basePrice = BigDecimal("10000.00")
                visitCharge = BigDecimal("2000.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = acServices.id
                name = "Annual AC Service Plan"
                basePrice = BigDecimal("15000.00")
                visitCharge = BigDecimal("0.00")
                isSubscription = true
                subscriptionDuration = 365
                imageUrl = null
            }

            // Carpentry Subservices
            SubServiceEntity.new {
                serviceId = carpentry.id
                name = "Furniture Assembly"
                basePrice = BigDecimal("3000.00")
                visitCharge = BigDecimal("500.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = carpentry.id
                name = "Door Repair"
                basePrice = BigDecimal("2500.00")
                visitCharge = BigDecimal("500.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = carpentry.id
                name = "Wood Maintenance Plan"
                basePrice = BigDecimal("8000.00")
                visitCharge = BigDecimal("0.00")
                isSubscription = true
                subscriptionDuration = 180
                imageUrl = null
            }

            // Painting Subservices
            SubServiceEntity.new {
                serviceId = painting.id
                name = "Single Room Paint"
                basePrice = BigDecimal("4000.00")
                visitCharge = BigDecimal("1000.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = painting.id
                name = "Full House Painting"
                basePrice = BigDecimal("20000.00")
                visitCharge = BigDecimal("3000.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }

            // Home Sanitization Subservices
            SubServiceEntity.new {
                serviceId = sanitization.id
                name = "Disinfection Service"
                basePrice = BigDecimal("3500.00")
                visitCharge = BigDecimal("500.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = sanitization.id
                name = "Office Sanitization"
                basePrice = BigDecimal("7000.00")
                visitCharge = BigDecimal("1000.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = sanitization.id
                name = "Monthly Sanitize Plan"
                basePrice = BigDecimal("9000.00")
                visitCharge = BigDecimal("0.00")
                isSubscription = true
                subscriptionDuration = 30
                imageUrl = null
            }

            // Gardening Subservices
            SubServiceEntity.new {
                serviceId = gardening.id
                name = "Lawn Mowing"
                basePrice = BigDecimal("2500.00")
                visitCharge = BigDecimal("500.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = gardening.id
                name = "Hedge Trimming"
                basePrice = BigDecimal("3000.00")
                visitCharge = BigDecimal("500.00")
                isSubscription = false
                subscriptionDuration = null
                imageUrl = null
            }
            SubServiceEntity.new {
                serviceId = gardening.id
                name = "Bi-weekly Garden Plan"
                basePrice = BigDecimal("6000.00")
                visitCharge = BigDecimal("0.00")
                isSubscription = true
                subscriptionDuration = 14
                imageUrl = null
            }
        }
    }
}


fun setServiceImageUrls(baseUrl: String = "http://10.20.6.60:8081/services_images") {
    transaction {
        ServiceEntity.find { Services.name eq "Cleaning" }.firstOrNull()?.apply {
            imageUrl = "$baseUrl/cleaning.png"
        }

        ServiceEntity.find { Services.name eq "Plumbing" }.firstOrNull()?.apply {
            imageUrl = "$baseUrl/plumbing.png"
        }

        ServiceEntity.find { Services.name eq "Electrical" }.firstOrNull()?.apply {
            imageUrl = "$baseUrl/electrical.png"
        }

        ServiceEntity.find { Services.name eq "Home Appliance Repair" }.firstOrNull()?.apply {
            imageUrl = "$baseUrl/home_appliance.png"
        }

        ServiceEntity.find { Services.name eq "Pest Control" }.firstOrNull()?.apply {
            imageUrl = "$baseUrl/pest_control.png"
        }

        ServiceEntity.find { Services.name eq "AC Services" }.firstOrNull()?.apply {
            imageUrl = "$baseUrl/ac_service.png"
        }

        ServiceEntity.find { Services.name eq "Carpentry" }.firstOrNull()?.apply {
            imageUrl = "$baseUrl/carpenter.png"
        }

        ServiceEntity.find { Services.name eq "Painting" }.firstOrNull()?.apply {
            imageUrl = "$baseUrl/painting.png"
        }

        ServiceEntity.find { Services.name eq "Home Sanitization" }.firstOrNull()?.apply {
            imageUrl = "$baseUrl/sanitizer.png"
        }

        ServiceEntity.find { Services.name eq "Gardening & Landscaping" }.firstOrNull()?.apply {
            imageUrl = "$baseUrl/gardening.png"
        }
    }
}

fun setSubServiceImageUrls(baseUrl: String = "http://10.20.6.60:8081/subservices_images") {
    transaction {
        mapOf(
            "Basic Home Cleaning" to "basic_home_cleaning.png",
            "Deep Cleaning" to "deep_cleaning.png",
            "Monthly Cleaning Plan" to "monthly_cleaning_plan.png",
            "Tap Leak Fix" to "tap_leak_fix.png",
            "Water Tank Installation" to "water_tank_installation.png",
            "Plumbing Checkup Plan" to "plumbing_checkup_plan.png",
            "Light Fitting" to "light_fitting.png",
            "Wiring Repair" to "wiring_repair.png",
            "Electrical Safety Plan" to "electrical_safety_plan.png",
            "Refrigerator Repair" to "refrigerator_repair.png",
            "Washing Machine Repair" to "washing_machine_repair.png",
            "Appliance Maintenance Plan" to "appliance_maintenance_plan.png",
            "Cockroach Treatment" to "cockroach_treatment.png",
            "Rodent Control" to "rodent_control.png",
            "Quarterly Pest Plan" to "quaterly_pest_plan.png",
            "AC Gas Refill" to "ac_gas_fill.png",
            "AC Installation" to "ac_installation.png",
            "Annual AC Service Plan" to "annual_ac_service_plan.png",
            "Furniture Assembly" to "furniture_assembly.png",
            "Door Repair" to "door_repair.png",
            "Wood Maintenance Plan" to "wood_maintenance_plan.png",
            "Single Room Paint" to "single_room_paint.png",
            "Full House Painting" to "full_house_painting.png",
            "Disinfection Service" to "disinfection_service.png",
            "Office Sanitization" to "office_sanitization.png",
            "Monthly Sanitize Plan" to "monthly_sanitize_plan.png",
            "Lawn Mowing" to "lawn_mowing.png",
            "Hedge Trimming" to "hedge_trimming.png",
            "Bi-weekly Garden Plan" to "bi_weekly_garden_plan.png"
        ).forEach { (name, file) ->
            SubServiceEntity.find { SubServices.name eq name }.firstOrNull()?.apply {
                imageUrl = "$baseUrl/$file"
            }
        }
    }
}



