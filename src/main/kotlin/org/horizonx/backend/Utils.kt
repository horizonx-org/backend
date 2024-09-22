package org.horizonx.backend

import io.minio.MinioClient
import io.minio.PutObjectArgs
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

fun resolveResourceKey(appId: UUID, file: String) = "app/$appId/$file"
fun resolveAppResource(s3Url: String, appId: UUID, file: String) = "$s3Url/${resolveResourceKey(appId, file)}"

inline fun <T, N> T.applyIfNotNull(
    guard: N?,
    block: T.(N) -> Unit,
): T = this.apply { if (guard !== null) { this.block(guard) } }


fun MultipartFile.upload(
    minio: MinioClient,
    bucket: String,
    appId: UUID,
    name: String,
    mimeType: String? = null,
) = this.inputStream.use { fileStream ->
    val putObjectArgs = PutObjectArgs.builder()
        .bucket(bucket)
        .`object`(resolveResourceKey(appId, name))
        .applyIfNotNull(mimeType ?: this.contentType) { this.contentType(it) }
        .stream(fileStream, this.size, -1)
        .build()

    minio.putObject(putObjectArgs)!!
}
