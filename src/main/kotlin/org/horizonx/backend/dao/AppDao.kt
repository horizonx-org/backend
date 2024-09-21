package org.horizonx.backend.dao

import org.horizonx.backend.entity.AppEntity
import org.jdbi.v3.spring5.JdbiRepository
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.util.UUID

private const val allFields = "id, name, developer_id, title, image_url, description, published"

@JdbiRepository
interface AppDao {

    @SqlQuery(
        """
        SELECT $allFields
          FROM app
         WHERE id = :id
        """
    )
    fun getById(id: UUID): AppEntity?

    @SqlQuery(
        """
        SELECT $allFields
          FROM app
         WHERE published
        """
    )
    fun list(): List<AppEntity>

    @SqlUpdate(
        """
        INSERT INTO app (id, name, developer_id, title, image_url, description, published)
        VALUES (:id, :name, :developerId, :title, :imageUrl, :description, :published)
        """
    )
    fun create(@BindBean appEntity: AppEntity)

    @SqlUpdate(
        """
        UPDATE app
           SET title = :title,
               image_url = :imageUrl,
               description = :description,
               published = :published
         WHERE id = :id
        """
    )
    fun update(@BindBean appEntity: AppEntity)
}
