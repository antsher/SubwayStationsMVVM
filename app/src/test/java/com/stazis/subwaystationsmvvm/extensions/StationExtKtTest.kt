package com.stazis.subwaystationsmvvm.extensions

import com.stazis.subwaystationsmvvm.model.entities.Station
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class StationExtKtTest {

    @Test
    fun `hasCorrectCoordinates IfBothIncorrect ReturnsFalse`() =
        assertFalse(Station("Уручча", 0.0, 0.0).hasCorrectCoordinates())

    @Test
    fun `hasCorrectCoordinates IfLatitudeIncorrect ReturnsFalse`() =
        assertFalse(Station("Уручча", 0.0, 27.687875).hasCorrectCoordinates())

    @Test
    fun `hasCorrectCoordinates IfLongitudeIncorrect ReturnsFalse`() =
        assertFalse(Station("Уручча", 53.9453522, 0.0).hasCorrectCoordinates())

    @Test
    fun `hasCorrectCoordinates IfBothCorrect ReturnsTrue`() =
        assertTrue(Station("Уручча", 53.9453522, 27.687875).hasCorrectCoordinates())
}