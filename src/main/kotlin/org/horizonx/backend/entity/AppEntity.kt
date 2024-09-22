package org.horizonx.backend.entity

import org.horizonx.backend.dto.AppFullDto
import java.util.UUID

data class AppEntity(
    val id: UUID,
    val name: String,
    val developerId: UUID,

    val title: String,
    val iconFile: String?,
    val bannerFile: String?,
    val screenshotFiles: List<String>,
    val description: String,
    val tags: List<String>,

    val published: Boolean,
)

fun AppEntity.mapToDto(s3Url: String): AppFullDto = AppFullDto(
    id = id,
    name = name,
    developerId = developerId,
    title = title,
    iconFile = iconFile,
    bannerFile = bannerFile,
    screenshotFiles = screenshotFiles,
    description = description,
    tags = tags,
    published = published,
    s3Url = s3Url,
)
