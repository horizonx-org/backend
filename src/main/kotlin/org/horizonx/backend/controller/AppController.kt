package org.horizonx.backend.controller

import org.horizonx.backend.exception.NotFoundException
import org.horizonx.backend.entity.AppEntity
import org.horizonx.backend.exception.ForbiddenException
import org.horizonx.backend.service.AppService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

private const val securityKey = "ahkiyah1aecohg8eed8xaimooJaibi3loh3kam4iaNg4eiVeineib7eeD4aiphab"

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

    @GetMapping("/")
    fun list(): List<AppEntity> {
        return appService.list()
    }

    @PostMapping("/")
    fun create(@RequestBody app: AppEntity, @RequestParam token: String): AppEntity {
        if (token != securityKey) { throw ForbiddenException("Wrong auth token") }
        return appService.create(app)
    }

    @PutMapping("/{id}")
    fun update(@RequestBody app: AppEntity, @PathVariable id: UUID, @RequestParam token: String): AppEntity {
        if (token != securityKey) { throw ForbiddenException("Wrong auth token") }
        return appService.update(id, app)
    }
}
