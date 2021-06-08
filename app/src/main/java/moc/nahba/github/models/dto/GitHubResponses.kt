package moc.nahba.github.models.dto

import com.squareup.moshi.Json

data class GitHubRepo(
    val name: String,
    val private: Boolean?,
    val owner: Owner,
)

data class Owner(
    val id: Long,
    @field:Json(name = "avatar_url") val avatarUrl: String?,
)