package org.horizonx.backend.entity

import org.horizonx.backend.dto.DtoMarker
import java.util.UUID

data class AppEntity(
    val id: UUID,
    val name: String,
    val developerId: UUID,

    val title: String,
    val imageUrl: String,
    val description: String,

    val isPublished: Boolean,
) : DtoMarker
