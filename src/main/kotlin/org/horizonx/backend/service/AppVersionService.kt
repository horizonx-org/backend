package org.horizonx.backend.service

import io.minio.MinioClient
import org.horizonx.backend.bootstrap.HorizonxConfig
import org.horizonx.backend.dao.AppDao
import org.horizonx.backend.dao.AppVersionDao
import org.horizonx.backend.dto.AppVersionCreateDto
import org.horizonx.backend.dto.AppVersionUpdateDto
import org.horizonx.backend.dto.apkName
import org.horizonx.backend.entity.AppVersionEntity
import org.horizonx.backend.exception.NotFoundException
import org.horizonx.backend.upload
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

private const val apkMime = "application/vnd.android.package-archive"

@Service
class AppVersionService(
    private val appDao: AppDao,
    private val appVersionDao: AppVersionDao,
    private val minio: MinioClient,
    private val config: HorizonxConfig,
) {
    fun getById(id: UUID): AppVersionEntity? {
        return appVersionDao.getById(id)
    }

    fun list(appId: UUID, all: Boolean): List<AppVersionEntity> {
        return appVersionDao.list(appId, all)
    }

    @Transactional
    fun create(appVersionCreateDto: AppVersionCreateDto, file: MultipartFile): AppVersionEntity {
        appDao.getById(appVersionCreateDto.appId) ?: throw NotFoundException("App not found")
        val id = UUID.randomUUID()
        appVersionDao.create(id, appVersionCreateDto)
        file.upload(
            minio = minio,
            bucket = config.s3.bucket,
            appId = appVersionCreateDto.appId,
            name = appVersionCreateDto.apkName,
            mimeType = apkMime,
        )
        return appVersionDao.getById(id)!!
    }

    @Transactional
    fun update(appId: UUID, id: UUID, appVersionUpdateDto: AppVersionUpdateDto): AppVersionEntity {
        appDao.getById(appId) ?: throw NotFoundException("App not found")
        appVersionDao.getById(id) ?: throw NotFoundException("App version not found")
        appVersionDao.update(id, appVersionUpdateDto)
        return appVersionDao.getById(id)!!
    }

    @Transactional
    fun uploadNewApk(appId: UUID, id: UUID, file: MultipartFile): AppVersionEntity {
        appDao.getById(appId) ?: throw NotFoundException("App not found")
        val oldVersion = appVersionDao.getById(id) ?: throw NotFoundException("App version not found")
        file.upload(
            minio = minio,
            bucket = config.s3.bucket,
            appId = appId,
            name = oldVersion.apkName,
            mimeType = apkMime,
        )
        appVersionDao.touch(id)
        return appVersionDao.getById(id)!!
    }
}
