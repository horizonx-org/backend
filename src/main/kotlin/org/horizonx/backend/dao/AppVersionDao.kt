package org.horizonx.backend.dao

import org.horizonx.backend.dto.AppVersionCreateDto
import org.horizonx.backend.dto.AppVersionUpdateDto
import org.horizonx.backend.entity.AppVersionEntity
import org.jdbi.v3.spring5.JdbiRepository
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.util.UUID

private const val allFields = "id, app_id, version_code, updated_at, published"

@JdbiRepository
interface AppVersionDao {

    @SqlQuery(
        """
        SELECT $allFields
          FROM app_version
         WHERE id = :id
        """
    )
    fun getById(id: UUID): AppVersionEntity?

    @SqlQuery(
        """
        SELECT $allFields
          FROM app_version
         WHERE app_id = :appId
           AND (:all OR published)
        """
    )
    fun list(appId: UUID, all: Boolean): List<AppVersionEntity>

    @SqlUpdate(
        """
        INSERT INTO app_version (id, app_id, version_code, updated_at, published)
        VALUES (:id, :appId, :versionCode, now(), false)
        """
    )
    fun create(id: UUID, @BindBean appVersionCreateDto: AppVersionCreateDto)

    @SqlUpdate(
        """
        UPDATE app_version SET
               updated_at = now(),
               published = :published
         WHERE id = :id
        """
    )
    fun update(id: UUID, @BindBean appUpdateDto: AppVersionUpdateDto)

    @SqlUpdate(
        """
        UPDATE app_version SET updated_at = now()
         WHERE id = :id
        """
    )
    fun touch(id: UUID)
}
