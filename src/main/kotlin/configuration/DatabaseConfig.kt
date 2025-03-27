package com.configuration

import com.entities.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
       val config = HikariConfig().apply {
           jdbcUrl = "jdbc:postgresql://localhost:5432/CallAGuy"
           driverClassName = "org.postgresql.Driver"
           username = "postgres"
           password = "1234"
           maximumPoolSize = 10
           isAutoCommit = false
           transactionIsolation = "TRANSACTION_REPEATABLE_READ"
       }
        Database.connect(HikariDataSource(config))

        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                Users,
                /*Services,
                SubServices,
                ServiceRequests,
                Payments*/
            )
        }
    }
}

