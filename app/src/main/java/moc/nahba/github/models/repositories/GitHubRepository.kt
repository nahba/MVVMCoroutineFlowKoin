package moc.nahba.github.models.repositories

import moc.nahba.github.models.datasources.GitHubRemoteSource

class GitHubRepository(
    private val dataSource: GitHubRemoteSource,
) {
    suspend fun repositories(userName: String) =
        dataSource.repositories(userName)
}