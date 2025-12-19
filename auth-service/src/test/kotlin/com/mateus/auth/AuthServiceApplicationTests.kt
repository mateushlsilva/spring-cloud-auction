package com.mateus.auth

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@Testcontainers
@TestPropertySource(
    properties = [
        "jwt.secret=test-secret",
        "jwt.expiration=3600"
    ]
)
@ActiveProfiles("test")
class AuthServiceApplicationTests() {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    companion object {
        @Container
        val postgres = PostgreSQLContainer("postgres:17.5-alpine3.21")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test")

        @JvmStatic
        @DynamicPropertySource
        fun registerProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
            registry.add("spring.datasource.driver-class-name") {
                "org.postgresql.Driver"
            }
        }
    }
	@Test
	fun contextLoads() {

        val result = jdbcTemplate.queryForObject("SELECT 1", Int::class.java)

        assertThat(result).isEqualTo(1)

        assertThat(postgres.isRunning).isTrue()
	}

}