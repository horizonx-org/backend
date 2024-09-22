package org.horizonx.backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(msg: String? = null, cause: Throwable? = null) : Exception(msg, cause)

@ResponseStatus(HttpStatus.FORBIDDEN)
class ForbiddenException(msg: String? = null, cause: Throwable? = null) : Exception(msg, cause)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadResourceNameException(resourceName: String) : Exception("Bad resource name `$resourceName`")

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadVersionCodeException : Exception("Bad version code")
