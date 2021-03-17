package ru.hsHelper.server

import org.junit.Test
import kotlin.test.assertEquals

class MainTest {
    @Test
    fun testProvider() {
        assertEquals(helloWorldProvider(), "Hello World")
    }
}