package moc.nahba.github

import moc.nahba.github.models.dto.GitHubRepo
import moc.nahba.github.models.dto.Owner

internal val repo = GitHubRepo(
    name = "nahba",
    private = false,
    owner = Owner(
        id = 123456789,
        avatarUrl = "https://www.nahba.com/nahba.png"
    )
)
internal val githubRepos = listOf(repo)