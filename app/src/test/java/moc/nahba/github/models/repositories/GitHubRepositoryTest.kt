package moc.nahba.github.models.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import moc.nahba.github.githubRepos
import moc.nahba.github.models.datasources.GitHubRemoteSource
import moc.nahba.github.models.wrappers.GitHubWrapper
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GitHubRepositoryTest {
    private val dispatcher = TestCoroutineDispatcher()
    private val userName: String = "nahba"
    
    @Mock
    lateinit var dataSource: GitHubRemoteSource
    private lateinit var repository: GitHubRepository
    
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)
        repository = GitHubRepository(dataSource)
    }
    
    @Test
    fun `With repositories`() {
        runBlocking {
            Mockito.`when`(dataSource.repositories(userName)).thenReturn(flowOf(GitHubWrapper.Success(value = githubRepos)))
            val flow = repository.repositories(userName)
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
            Mockito.`when`(dataSource.repositories(userName)).thenReturn(flowOf(GitHubWrapper.NetworkError))
            val flow = repository.repositories(userName)
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
            Mockito.`when`(dataSource.repositories(userName)).thenReturn(flowOf(GitHubWrapper.GenericError()))
            val flow = repository.repositories(userName)
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