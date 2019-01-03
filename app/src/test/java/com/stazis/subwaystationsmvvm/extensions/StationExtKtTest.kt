package com.stazis.subwaystationsmvvm.extensions

import com.stazis.subwaystationsmvvm.model.entities.Station
import junit.framework.TestCase.assertFalse
import org.junit.Test

class StationExtKtTest {

    @Test
    fun station_hasCorrectCoordinates_returnsFalseIfBothIncorrect() =
        assertFalse(Station("Уручча", 0.0, 0.0).hasCorrectCoordinates())

    @Test
    fun station_hasCorrectCoordinates_returnsFalseIfLatitudeIncorrect() =
        assertFalse(Station("Уручча", 0.0, 27.687875).hasCorrectCoordinates())

    @Test
    fun station_hasCorrectCoordinates_returnsFalseIfLongitudeIncorrect() =
        assertFalse(Station("Уручча", 53.9453522, 0.0).hasCorrectCoordinates())

    @Test
    fun station_hasCorrectCoordinates_returnsTrueIfBothCorrect() =
        assert(Station("Уручча", 53.9453522, 27.687875).hasCorrectCoordinates())
}