package org.horizonx.backend.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import org.horizonx.backend.resolveAppResource
import java.util.UUID

interface AppIdentityHolder {
    val name: String
    val developerId: UUID
}

interface AppMetaHolder {
    val title: String
    val iconFile: String?
    val bannerFile: String?
    val screenshotFiles: List<String>
    val description: String
    val tags: List<String>
}

interface AppStatusHolder {
    val published: Boolean
}

interface AppUpdateHolder : AppMetaHolder, AppStatusHolder
interface AppCreateHolder : AppIdentityHolder, AppMetaHolder

interface AppFullHolder : HorizonxIdHolder, AppIdentityHolder, AppMetaHolder, AppStatusHolder {
    val iconUrl: String
    val bannerUrl: String?
    val screenshotUrls: List<String>
}

data class AppFullDto(
    override val id: UUID,
    override val name: String,
    override val developerId: UUID,

    override val title: String,
    override val iconFile: String?,
    override val bannerFile: String?,
    override val screenshotFiles: List<String>,
    override val description: String,
    override val tags: List<String>,

    override val published: Boolean,

    @JsonIgnore
    @Schema(hidden = true)
    val s3Url: String,

) : AppFullHolder {
    override val iconUrl get() = iconFile
        ?.let { resolveAppResource(s3Url, id, it) }
        ?: "$s3Url/no-image.png"

    override val bannerUrl get() = bannerFile?.let { resolveAppResource(s3Url, id, it) }
    override val screenshotUrls get() = screenshotFiles.map { resolveAppResource(s3Url, id, it) }
}

data class AppCreateDto(
    override val name: String,
    override val developerId: UUID,

    override val title: String,
    override val iconFile: String?,
    override val bannerFile: String?,
    override val screenshotFiles: List<String>,
    override val description: String,
    override val tags: List<String>,
) : AppCreateHolder

data class AppUpdateDto(
    override val title: String,
    override val iconFile: String?,
    override val bannerFile: String?,
    override val screenshotFiles: List<String>,
    override val description: String,
    override val tags: List<String>,

    override val published: Boolean,
) : AppUpdateHolder

data class ResourceUploadResultDto(
    override val id: UUID,
    val fileName: String,
    val fileUrl: String,
) : HorizonxIdHolder
