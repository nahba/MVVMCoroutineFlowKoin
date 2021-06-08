package moc.nahba.github

import kotlinx.coroutines.ExperimentalCoroutinesApi
import moc.nahba.github.models.datasources.GitHubRemoteSourceTest
import moc.nahba.github.models.dto.GitHubResponsesTest
import moc.nahba.github.models.repositories.GitHubRepositoryTest
import moc.nahba.github.models.usecases.GitHubUseCaseTest
import moc.nahba.github.models.wrappers.GitHubWrapperTest
import moc.nahba.github.views.viewmodels.GitHubViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@ExperimentalCoroutinesApi
@Suite.SuiteClasses(
    GitHubRemoteSourceTest::class,
    GitHubResponsesTest::class,
    GitHubRepositoryTest::class,
    GitHubUseCaseTest::class,
    GitHubViewModelTest::class,
    GitHubWrapperTest::class,
)
class NahbaSuite