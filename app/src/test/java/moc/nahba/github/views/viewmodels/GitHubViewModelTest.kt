package moc.nahba.github.views.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import moc.nahba.github.githubRepos
import moc.nahba.github.models.usecases.GitHubUseCase
import moc.nahba.github.models.wrappers.GitHubWrapper
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GitHubViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
    
    private val dispatcher = TestCoroutineDispatcher()
    private val userName: String = "nahba"
    
    @Mock
    lateinit var useCase: GitHubUseCase
    private lateinit var viewModel: GitHubViewModel
    
    private val observer : Observer<GitHubWrapper> = mock()
    
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)
        viewModel = GitHubViewModel(useCase = useCase)
    }
    
    @Test
    @Throws(Exception::class)
    fun `Test loader`() {
        viewModel.repositories.observeForever(observer)
    }
    
    @Test
    fun `With success`() {
        runBlocking {
            Mockito.`when`(useCase.repositories(userName)).thenReturn(flowOf(GitHubWrapper.Success(value = githubRepos)))
            viewModel.repositories(userName)
            assertEquals(viewModel.repositories.value, GitHubWrapper.Success(value = githubRepos))
        }
    }
    
    @Test
    fun `With network error`() {
        runBlocking {
            Mockito.`when`(useCase.repositories(userName)).thenReturn(flowOf(GitHubWrapper.NetworkError))
            viewModel.repositories(userName)
            assertEquals(viewModel.repositories.value, GitHubWrapper.NetworkError)
        }
    }
    
    @Test
    fun `With generic error`() {
        runBlocking {
            Mockito.`when`(useCase.repositories(userName)).thenReturn(flowOf(GitHubWrapper.GenericError()))
            viewModel.repositories(userName)
            assertEquals(viewModel.repositories.value, GitHubWrapper.GenericError())
        }
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}