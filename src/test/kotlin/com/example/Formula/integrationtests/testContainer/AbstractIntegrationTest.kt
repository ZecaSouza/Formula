package com.example.Formula.integrationtests.testContainer

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.lifecycle.Startables
import org.testcontainers.utility.DockerImageName
import java.util.stream.Stream

@ContextConfiguration(initializers = [AbstractIntegrationTest.Initializer::class])
open class AbstractIntegrationTest {

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            startContainers()

            val environment = applicationContext.environment
            val testContainer = MapPropertySource(
                "testContainer", createConnectionConfiguration()
            )
            environment.propertySources.addFirst(testContainer)
        }

        companion object {
            private val mysql: MySQLContainer<*> = MySQLContainer(DockerImageName.parse("mysql:8.0.28"))
                .withDatabaseName("testdb")
                .withUsername("testuser")
                .withPassword("testpass")

            private fun startContainers() {
                Startables.deepStart(Stream.of(mysql)).join()
            }

            private fun createConnectionConfiguration(): MutableMap<String, Any> {
                return mutableMapOf(
                    "spring.datasource.url" to mysql.jdbcUrl,
                    "spring.datasource.username" to mysql.username,
                    "spring.datasource.password" to mysql.password,
                    "spring.datasource.driver-class-name" to "com.mysql.cj.jdbc.Driver"
                )
            }
        }
    }
}