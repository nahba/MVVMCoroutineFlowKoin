package moc.nahba.github.models.services

import moc.nahba.github.models.dto.GitHubRepo
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("users/{userName}/repos")
    suspend fun repositories(@Path(value = "userName") userName : String) : List<GitHubRepo>
}