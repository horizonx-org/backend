package org.horizonx.backend.service

import org.horizonx.backend.dao.AppDao
import org.horizonx.backend.entity.AppEntity
import org.horizonx.backend.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class AppService(
    private val appDao: AppDao
) {
    fun getById(id: UUID): AppEntity? {
        return appDao.getById(id)
    }

    fun list(): List<AppEntity> {
        return appDao.list()
    }

    @Transactional
    fun create(appEntity: AppEntity): AppEntity {
        val newApp = appEntity.copy(id = UUID.randomUUID())
        appDao.create(newApp)
        return appDao.getById(newApp.id)!!
    }

    @Transactional
    fun update(id: UUID, appEntity: AppEntity): AppEntity {
        appDao.getById(id) ?: throw NotFoundException("App not found")
        val updatedApp = appEntity.copy(id = id)
        appDao.update(updatedApp)
        return appDao.getById(id)!!
    }
}
