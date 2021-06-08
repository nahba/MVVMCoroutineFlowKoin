package moc.nahba.github.models.datasources

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import moc.nahba.github.githubRepos
import moc.nahba.github.models.services.GitHubApi
import moc.nahba.github.models.wrappers.GitHubWrapper
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.io.IOException
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GitHubRemoteSourceTest {
    private val dispatcher = TestCoroutineDispatcher()
    private val userName: String = "nahba"
    
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)
    }
    
    @Test
    fun `With repositories`() {
        runBlocking {
            val apiService = mock<GitHubApi> {
                onBlocking { repositories(userName) } doReturn githubRepos
            }
            val dataSource = GitHubRemoteSource(apiService, dispatcher)
            val flow = dataSource.repositories(userName)
            flow.collect { result ->
                when (result) {
                    is GitHubWrapper.Success<*> -> {
                        assertEquals(result.value, githubRepos)
                    }
                    else -> {
                    }
                }
            }
        }
    }
    
    @Test
    fun `With network error`() {
        runBlocking {
            val apiService = mock<GitHubApi> {
                onBlocking { repositories(userName) } doAnswer {
                    throw IOException()
                }
            }
            val dataSource = GitHubRemoteSource(apiService, dispatcher)
            val flow = dataSource.repositories(userName)
            flow.collect { result ->
                when (result) {
                    is GitHubWrapper.NetworkError -> {
                        assertEquals(result, GitHubWrapper.NetworkError)
                    }
                    else -> {
                    }
                }
            }
        }
    }
    
    @Test
    fun `With generic error`() {
        runBlocking {
            val apiService = mock<GitHubApi> {
                onBlocking { repositories(userName) } doAnswer {
                    throw Exception()
                }
            }
            val dataSource = GitHubRemoteSource(apiService, dispatcher)
            val flow = dataSource.repositories(userName)
            flow.collect { result ->
                when (result) {
                    is GitHubWrapper.GenericError -> {
                        assertEquals(result, GitHubWrapper.GenericError())
                    }
                    else -> {
                    }
                }
            }
        }
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}