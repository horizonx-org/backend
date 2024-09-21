package org.horizonx.backend

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.horizonx.backend.bootstrap.HorizonxConfig
import org.jdbi.v3.spring5.EnableJdbiRepositories
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity


@OpenAPIDefinition(servers = [Server(url = "/", description = "Default Server URL")])
@EnableWebSecurity
@EnableJdbiRepositories
@EnableConfigurationProperties(HorizonxConfig::class)
@SpringBootApplication
class BackendApplication

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}
