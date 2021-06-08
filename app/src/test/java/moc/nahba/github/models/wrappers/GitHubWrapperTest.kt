package moc.nahba.github.models.wrappers

import org.junit.Test
import org.mockito.kotlin.any
import kotlin.test.assertIs
import kotlin.test.assertNotNull

class GitHubWrapperTest {
    @Test
    fun `With success`() {
        val mock = GitHubWrapper.Success<Any>(value = any())
        assertNotNull(mock)
        assertIs<GitHubWrapper.Success<Any>>(mock)
    }
    
    @Test
    fun `With network error`() {
        val mock = GitHubWrapper.NetworkError
        assertNotNull(mock)
        assertIs<GitHubWrapper.NetworkError>(mock)
    }
    
    @Test
    fun `With generic`() {
        val mock = GitHubWrapper.GenericError(code = 0, error = any())
        assertNotNull(mock)
        assertIs<GitHubWrapper.GenericError>(mock)
    }
}