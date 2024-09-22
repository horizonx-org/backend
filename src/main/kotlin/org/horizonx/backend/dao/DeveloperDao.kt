package org.horizonx.backend.dao

import org.horizonx.backend.entity.DeveloperEntity
import org.jdbi.v3.spring5.JdbiRepository
import org.jdbi.v3.sqlobject.statement.SqlQuery
import java.util.UUID

@JdbiRepository
interface DeveloperDao {

    @SqlQuery(
        """
        SELECT id, name, title
          FROM developer
         WHERE id = :id
        """
    )
    fun getById(id: UUID): DeveloperEntity?

    @SqlQuery(
        """
        SELECT id, name, title
          FROM developer
        """
    )
    fun list(): List<DeveloperEntity>
}
