package org.horizonx.backend.dao

import org.horizonx.backend.entity.AppEntity
import org.jdbi.v3.spring5.JdbiRepository
import org.jdbi.v3.sqlobject.statement.SqlQuery
import java.util.UUID

@JdbiRepository
interface AppDao {

    @SqlQuery(
        """
        SELECT id, name, developer_id, title, image_url, description, is_published
          FROM app
         WHERE id = :id
        """
    )
    fun getById(id: UUID): AppEntity?
}
