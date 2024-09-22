package org.horizonx.backend.controller

import org.horizonx.backend.entity.DeveloperEntity
import org.horizonx.backend.exception.NotFoundException
import org.horizonx.backend.service.DeveloperService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(
    path = ["/developer"],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
class DeveloperController(
    private val developerService: DeveloperService,
) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): DeveloperEntity {
        return developerService.getById(id) ?: throw NotFoundException("Developer not found")
    }

    @GetMapping("/")
    fun list(): List<DeveloperEntity> {
        return developerService.list()
    }
}
