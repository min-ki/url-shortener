package nrise.urlshortener.util

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class EncoderTest {
    @Test
    fun `encodeToID should return 6 characters`() {
        val id = "https://quat.life".encodeToID()
        assertEquals(6, id.length)
    }
}
