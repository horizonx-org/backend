package org.horizonx.backend.controller

import org.horizonx.backend.NotFoundException
import org.horizonx.backend.entity.AppEntity
import org.horizonx.backend.service.AppService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

private val securityKey = "ahkiyah1aecohg8eed8xaimooJaibi3loh3kam4iaNg4eiVeineib7eeD4aiphab"

@RestController
@RequestMapping(
    path = ["/app"],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
class AppController(
    private val appService: AppService
) {
    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): AppEntity {
        return appService.getById(id) ?: throw NotFoundException("App not found")
    }
}
