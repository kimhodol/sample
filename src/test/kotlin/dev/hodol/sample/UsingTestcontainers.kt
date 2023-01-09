package dev.hodol.sample

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
interface UsingTestcontainers {
    companion object {
        @JvmStatic
        @Container
        val container: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:latest")
            .withDatabaseName("testdb")
    }
}