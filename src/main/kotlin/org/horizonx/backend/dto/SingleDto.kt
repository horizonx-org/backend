package org.horizonx.backend.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

interface DtoMarker

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SingleDto<T: DtoMarker>(
    val msg: String?,
    val obj: T?,
    val id: UUID?,
)

fun SingleDto(msg: String) = SingleDto(msg, null, null)
