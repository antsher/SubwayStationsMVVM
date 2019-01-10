package com.stazis.subwaystationsmvvm.di

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.koin.test.KoinTest

private const val EXPECTED_MODULES_QUANTITY = 4

class KoinModulesKtTest : KoinTest {

    @Test
    fun `getKoinModules IfAllCorrect ReturnsAll`() {
        val modules = getKoinModules()
        assertNotNull(modules)
        assertEquals(modules.size, EXPECTED_MODULES_QUANTITY)
    }
}