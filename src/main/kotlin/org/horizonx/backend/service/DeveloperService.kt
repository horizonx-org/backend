package org.horizonx.backend.service

import org.horizonx.backend.dao.DeveloperDao
import org.horizonx.backend.entity.DeveloperEntity
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DeveloperService(
    private val developerDao: DeveloperDao,
) {
    fun getById(id: UUID): DeveloperEntity? {
        return developerDao.getById(id)
    }

    fun list(): List<DeveloperEntity> {
        return developerDao.list()
    }
}
