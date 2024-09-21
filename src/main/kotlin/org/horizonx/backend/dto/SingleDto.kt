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
fun <T: DtoMarker> SingleDto(obj: T) = SingleDto(null, obj, null)
fun SingleDto(id: UUID) = SingleDto(null, null, id)
fun SingleDto(msg: String, id: UUID) = SingleDto(msg, null, id)
fun <T: DtoMarker> T?.wrap(): SingleDto<T> = this?.let { SingleDto(it) } ?: SingleDto("Not found", null, null)
