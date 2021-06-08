package moc.nahba.github.models.dto

import moc.nahba.github.repo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GitHubResponsesTest {
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }
    
    @Test
    fun `Check GitHubRepo`() {
        val mockRepo = repo
        assertNotNull(mockRepo)
        assertEquals(mockRepo.name, "nahba")
        assertEquals(mockRepo.private, false)
        assertNotNull(mockRepo.owner)
        assertEquals(mockRepo.owner.id, 123456789)
        assertEquals(mockRepo.owner.avatarUrl, "https://www.nahba.com/nahba.png")
    }
    
    @After
    fun tearDown() {}
}