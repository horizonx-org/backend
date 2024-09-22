package org.horizonx.backend.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import org.horizonx.backend.resolveAppResource
import java.time.Instant
import java.util.UUID

interface AppVersionIdentityHolder {
    val appId: UUID
    val versionCode: String
}

interface AppVersionStatusHolder {
    val published: Boolean
}

interface AppVersionCreateHolder : AppVersionIdentityHolder
interface AppVersionUpdateHolder : AppVersionStatusHolder

interface AppVersionFullHolder : HorizonxIdHolder, AppVersionIdentityHolder, AppVersionStatusHolder {
    val updatedAt: Instant
    val apkUrl: String
}

data class AppVersionCreateDto(
    override val appId: UUID,
    override val versionCode: String,
) : AppVersionCreateHolder

data class AppVersionUpdateDto(
    override val published: Boolean,
) : AppVersionUpdateHolder

data class AppVersionFullDto(
    override val id: UUID,
    override val appId: UUID,
    override val versionCode: String,
    override val updatedAt: Instant,
    override val published: Boolean,

    @JsonIgnore
    @Schema(hidden = true)
    val s3Url: String,

) : AppVersionFullHolder {
    override val apkUrl get() = resolveAppResource(s3Url, appId, apkName)
}

val AppVersionIdentityHolder.apkName get() = "versions/$versionCode.apk"
