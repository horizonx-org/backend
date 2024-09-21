package org.horizonx.backend.bootstrap

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.core.statement.Slf4JSqlLogger
import org.jdbi.v3.postgres.PostgresPlugin
import org.jdbi.v3.spring5.SpringConnectionFactory
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class JdbiConfiguration {

    @Bean
    fun jdbi(ds: DataSource): Jdbi {
        val cf = SpringConnectionFactory(ds)
        val jdbi = Jdbi.create(cf)

        jdbi.setSqlLogger(Slf4JSqlLogger())
        jdbi.installPlugin(PostgresPlugin())
        jdbi.installPlugin(KotlinSqlObjectPlugin())
        jdbi.installPlugin(KotlinPlugin())

        return jdbi
    }
}
