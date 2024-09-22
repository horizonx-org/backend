package org.horizonx.backend.controller

import org.horizonx.backend.bootstrap.HorizonxConfig
import org.horizonx.backend.dto.AppCreateDto
import org.horizonx.backend.dto.AppFullDto
import org.horizonx.backend.dto.AppUpdateDto
import org.horizonx.backend.dto.AppVersionCreateDto
import org.horizonx.backend.dto.AppVersionFullDto
import org.horizonx.backend.dto.AppVersionUpdateDto
import org.horizonx.backend.dto.ResourceUploadResultDto
import org.horizonx.backend.entity.mapToDto
import org.horizonx.backend.exception.BadResourceNameException
import org.horizonx.backend.exception.BadVersionCodeException
import org.horizonx.backend.exception.NotFoundException
import org.horizonx.backend.exception.ForbiddenException
import org.horizonx.backend.service.AppService
import org.horizonx.backend.service.AppVersionService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

private const val securityKey = "ahkiyah1aecohg8eed8xaimooJaibi3loh3kam4iaNg4eiVeineib7eeD4aiphab"

private val fileNameRegex = "([a-zA-Z0-9._-]{1,30}/){0,5}[a-zA-Z0-9._-]{1,30}".toRegex()
private val versionCodeRegex = "[0-9]{1,9}(\\.[0-9]{1,9}){0,2}".toRegex()

@RestController
@RequestMapping(
    path = ["/app"],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
class AppController(
    private val appService: AppService,
    private val appVersionService: AppVersionService,
    private val config: HorizonxConfig,
) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): AppFullDto {
        val appEntity = appService.getById(id) ?: throw NotFoundException("App not found")
        return appEntity.mapToDto(config.s3.hostBucket)
    }

    @GetMapping("/")
    fun list(@RequestParam(required = false) all: Boolean = false): List<AppFullDto> {
        val data = appService.list(all)
        return data.map { it.mapToDto(config.s3.hostBucket) }
    }

    @PostMapping("/", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody app: AppCreateDto, @RequestParam token: String): AppFullDto {
        if (token != securityKey) { throw ForbiddenException("Wrong auth token") }
        val appEntity = appService.create(app)
        return appEntity.mapToDto(config.s3.hostBucket)
    }

    @PutMapping("/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun update(@RequestBody app: AppUpdateDto, @PathVariable id: UUID, @RequestParam token: String): AppFullDto {
        if (token != securityKey) { throw ForbiddenException("Wrong auth token") }
        val appEntity = appService.update(id, app)
        return appEntity.mapToDto(config.s3.hostBucket)
    }

    @PostMapping("/{id}/resource", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
	fun uploadResource(
        @PathVariable id: UUID,
        @RequestParam name: String,
        @RequestParam file: MultipartFile,
        @RequestParam token: String,
    ): ResourceUploadResultDto {
        if (token != securityKey) { throw ForbiddenException("Wrong auth token") }
        if (!name.matches(fileNameRegex)) { throw BadResourceNameException(name) }
        if (name.lowercase().startsWith("version")) { throw BadResourceNameException(name) }

        return appService.uploadResource(id, name, file)
	}

    @GetMapping("/{appId}/version/{id}")
    fun getVersionById(@PathVariable appId: UUID, @PathVariable id: UUID): AppVersionFullDto {
        appService.getById(appId) ?: throw NotFoundException("App not found")
        val appVersion = appVersionService.getById(id) ?: throw NotFoundException("App version not found")
        return appVersion.mapToDto(config.s3.hostBucket)
    }

    @GetMapping("/{appId}/version")
    fun listVersions(
        @PathVariable appId: UUID,
        @RequestParam(required = false) all: Boolean = false,
    ): List<AppVersionFullDto> {
        appService.getById(appId) ?: throw NotFoundException("App not found")
        val data = appVersionService.list(appId, all)
        return data.map { it.mapToDto(config.s3.hostBucket) }
    }

    @PostMapping("/{appId}/version", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createVersion(
        @PathVariable appId: UUID,
        @RequestParam versionCode: String,
        @RequestParam apk: MultipartFile,
        @RequestParam token: String,
    ): AppVersionFullDto {
        if (token != securityKey) { throw ForbiddenException("Wrong auth token") }
        if (!versionCode.matches(versionCodeRegex)) { throw BadVersionCodeException() }

        val appVersion = appVersionService.create(AppVersionCreateDto(appId, versionCode), apk)
        return appVersion.mapToDto(config.s3.hostBucket)
    }

    @PutMapping("/{appId}/version/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateVersion(
        @PathVariable appId: UUID,
        @PathVariable id: UUID,
        @RequestBody version: AppVersionUpdateDto,
        @RequestParam token: String,
    ): AppVersionFullDto {
        if (token != securityKey) { throw ForbiddenException("Wrong auth token") }
        val appVersion = appVersionService.update(appId, id, version)
        return appVersion.mapToDto(config.s3.hostBucket)
    }

    @PostMapping("/{appId}/version/{id}/newApk", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
	fun uploadVersionApk(
        @PathVariable appId: UUID,
        @PathVariable id: UUID,
        @RequestParam file: MultipartFile,
        @RequestParam token: String,
    ): AppVersionFullDto {
        if (token != securityKey) { throw ForbiddenException("Wrong auth token") }

        return appVersionService.uploadNewApk(appId, id, file).mapToDto(config.s3.hostBucket)
	}
}
