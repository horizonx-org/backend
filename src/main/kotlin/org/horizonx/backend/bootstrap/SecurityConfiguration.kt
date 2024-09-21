package org.horizonx.backend.bootstrap

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfiguration {
    @Bean
    fun security(http: HttpSecurity): SecurityFilterChain = http
        .authorizeHttpRequests { it.anyRequest().permitAll() }
        .formLogin { it.disable() }
        .csrf { it.disable() }
        .build()
}
