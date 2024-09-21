package org.horizonx.backend.dao

import org.horizonx.backend.entity.AppEntity
import org.jdbi.v3.spring5.JdbiRepository
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.util.UUID

@JdbiRepository
interface AppDao {

    @SqlQuery(
        """
        SELECT id, name, developer_id, title, image_url, description, published
          FROM app
         WHERE id = :id
        """
    )
    fun getById(id: UUID): AppEntity?

    @SqlUpdate(
        """
        INSERT INTO app (id, name, developer_id, title, image_url, description, published)
        VALUES (:id, :name, :developerId, :title, :imageUrl, :description, :published)
        """
    )
    fun create(@BindBean appEntity: AppEntity)
}
