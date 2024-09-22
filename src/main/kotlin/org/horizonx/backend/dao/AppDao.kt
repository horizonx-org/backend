package org.horizonx.backend.dao

import org.horizonx.backend.dto.AppCreateDto
import org.horizonx.backend.dto.AppUpdateDto
import org.horizonx.backend.entity.AppEntity
import org.jdbi.v3.spring5.JdbiRepository
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.util.UUID

private const val allFields = """
    id,
    name,
    developer_id,
    
    title,
    icon_file,
    banner_file,
    screenshot_files,
    description,
    tags,
    
    published
"""

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
         WHERE :all OR published
        """
    )
    fun list(all: Boolean): List<AppEntity>

    @SqlUpdate(
        """
        INSERT INTO app (
                id, name, developer_id, title,
                icon_file, banner_file, screenshot_files,
                description, tags, published
        )
        VALUES (
                :id, :name, :developerId, :title,
                :iconFile, :bannerFile, :screenshotFiles,
                :description, :tags, false
        )
        """
    )
    fun create(id: UUID, @BindBean appCreateDto: AppCreateDto)

    @SqlUpdate(
        """
        UPDATE app SET
               title = :title,
               icon_file = :iconFile,
               banner_file = :bannerFile,
               screenshot_files = :screenshotFiles,
               description = :description,
               tags = :tags,
               published = :published
         WHERE id = :id
        """
    )
    fun update(id: UUID, @BindBean appUpdateDto: AppUpdateDto)
}
