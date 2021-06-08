package moc.nahba.github.models.datasources

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import moc.nahba.github.models.services.GitHubApi
import moc.nahba.github.models.wrappers.GitHubWrapper
import retrofit2.HttpException
import java.io.IOException

class GitHubRemoteSource(
    private val apiService: GitHubApi,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun repositories(userName: String) = flow<GitHubWrapper> {
        emit(
            GitHubWrapper.Success(value = apiService.repositories(userName))
        )
    }
        .cancellable()
        .flowOn(dispatcher)
        .catch { throwable ->
            when (throwable) {
                is IOException -> emit(
                    GitHubWrapper.NetworkError
                )
                is HttpException -> emit(
                    GitHubWrapper.GenericError(code = throwable.code(), error = throwable.message())
                )
                else -> emit(
                    GitHubWrapper.GenericError(code = null, error = null)
                )
            }
        }
}