package moc.nahba.github.models.wrappers

sealed class GitHubWrapper {
    data class Success<out T>(val value: T): GitHubWrapper()
    data class GenericError(val code: Int? = null, val error: String? = null): GitHubWrapper()
    object NetworkError: GitHubWrapper()
}