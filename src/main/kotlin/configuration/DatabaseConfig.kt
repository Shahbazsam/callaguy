package com.configuration

import com.entities.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {

        // migrateDataBase()

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

        /*transaction {
            SchemaUtils.createMissingTablesAndColumns(
                Customers,
                Professionals,
                Services,
                SubServices,
                ServiceRequests,
                SupportMessages,
                SupportTickets,
                Payments,
                ProfessionalServices,
                Admin
            )
        }*/
    }

    private fun migrateDataBase() {
        val flyway = Flyway.configure()
            .dataSource("jdbc:postgresql://localhost:5432/CallAGuy", "postgres", "1234")
            .locations("classpath:db/migration")
            .load()

        flyway.migrate()
    }
}

