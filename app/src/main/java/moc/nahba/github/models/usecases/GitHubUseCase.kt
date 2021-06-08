package moc.nahba.github.models.usecases

import moc.nahba.github.models.repositories.GitHubRepository

class GitHubUseCase(
    private val repository: GitHubRepository,
) {
    suspend fun repositories(userName: String) =
        repository.repositories(userName)
}