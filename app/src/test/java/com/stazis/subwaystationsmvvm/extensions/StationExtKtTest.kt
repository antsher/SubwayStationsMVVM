package com.stazis.subwaystationsmvvm.extensions

import com.stazis.subwaystationsmvvm.model.entities.Station
import org.junit.Assert.assertFalse
import org.junit.Test

class StationExtKtTest {

    @Test
    fun stationHasCorrectCoordinates_ifBothIncorrect_returnsFalse() =
        assertFalse(Station("Уручча", 0.0, 0.0).hasCorrectCoordinates())

    @Test
    fun stationHasCorrectCoordinates_ifLatitudeIncorrect_returnsFalse() =
        assertFalse(Station("Уручча", 0.0, 27.687875).hasCorrectCoordinates())

    @Test
    fun stationHasCorrectCoordinates_ifLongitudeIncorrect_returnsFalse() =
        assertFalse(Station("Уручча", 53.9453522, 0.0).hasCorrectCoordinates())

    @Test
    fun stationHasCorrectCoordinates_ifBothCorrect_returnsTrue() =
        assert(Station("Уручча", 53.9453522, 27.687875).hasCorrectCoordinates())
}