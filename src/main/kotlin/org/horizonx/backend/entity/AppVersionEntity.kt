package org.horizonx.backend.entity

import org.horizonx.backend.dto.AppVersionFullDto
import org.horizonx.backend.dto.AppVersionIdentityHolder
import java.time.Instant
import java.util.UUID

data class AppVersionEntity(
    val id: UUID,
    override val appId: UUID,
    override val versionCode: String,
    val updatedAt: Instant,
    val published: Boolean,
) : AppVersionIdentityHolder

fun AppVersionEntity.mapToDto(s3Url: String): AppVersionFullDto = AppVersionFullDto(
    id = id,
    appId = appId,
    versionCode = versionCode,
    updatedAt = updatedAt,
    published = published,
    s3Url = s3Url,
)
