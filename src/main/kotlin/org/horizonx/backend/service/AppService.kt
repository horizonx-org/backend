package org.horizonx.backend.service

import io.minio.MinioClient
import org.horizonx.backend.bootstrap.HorizonxConfig
import org.horizonx.backend.dao.AppDao
import org.horizonx.backend.dto.AppCreateDto
import org.horizonx.backend.dto.AppUpdateDto
import org.horizonx.backend.dto.ResourceUploadResultDto
import org.horizonx.backend.entity.AppEntity
import org.horizonx.backend.exception.NotFoundException
import org.horizonx.backend.resolveAppResource
import org.horizonx.backend.upload
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class AppService(
    private val appDao: AppDao,
    private val minio: MinioClient,
    private val config: HorizonxConfig,
) {
    fun getById(id: UUID): AppEntity? {
        return appDao.getById(id)
    }

    fun list(all: Boolean): List<AppEntity> {
        return appDao.list(all)
    }

    @Transactional
    fun create(appCreateDto: AppCreateDto): AppEntity {
        val id = UUID.randomUUID()
        appDao.create(id, appCreateDto)
        return appDao.getById(id)!!
    }

    @Transactional
    fun update(id: UUID, appUpdateDto: AppUpdateDto): AppEntity {
        appDao.getById(id) ?: throw NotFoundException("App not found")
        appDao.update(id, appUpdateDto)
        return appDao.getById(id)!!
    }

    fun uploadResource(appId: UUID, name: String, file: MultipartFile): ResourceUploadResultDto {
        appDao.getById(appId) ?: throw NotFoundException("App not found")
        file.upload(minio, config.s3.bucket, appId, name)
        return ResourceUploadResultDto(appId, name, resolveAppResource(config.s3.hostBucket, appId, name))
    }
}

