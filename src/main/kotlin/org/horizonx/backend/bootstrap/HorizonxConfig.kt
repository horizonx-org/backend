package org.horizonx.backend.bootstrap
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("horizonx")
data class HorizonxConfig(
    val s3: S3Config,
)

data class S3Config(
    val externalHost: String,
    val host: String,
    val user: String,
    val pass: String,
) {
    val bucket get() = "horizonx"
    val hostBucket get() = "$externalHost/$bucket"
}
