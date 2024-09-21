package org.horizonx.backend.service

import org.horizonx.backend.dao.AppDao
import org.horizonx.backend.entity.AppEntity
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AppService(
    private val appDao: AppDao
) {
    fun getById(id: UUID): AppEntity? {
        return appDao.getById(id)
    }
}