package com.stazis.subwaystationsmvvm.extensions

import com.stazis.subwaystationsmvvm.model.entities.Station
import junit.framework.TestCase.assertFalse
import org.junit.Test

class StationExtKtTest {

    @Test
    fun stationHasCorrectCoordinates_IfBothIncorrect_returnsFalse() =
        assertFalse(Station("Уручча", 0.0, 0.0).hasCorrectCoordinates())

    @Test
    fun stationHasCorrectCoordinates_IfLatitudeIncorrect_returnsFalse() =
        assertFalse(Station("Уручча", 0.0, 27.687875).hasCorrectCoordinates())

    @Test
    fun stationHasCorrectCoordinates_IfLongitudeIncorrect_returnsFalse() =
        assertFalse(Station("Уручча", 53.9453522, 0.0).hasCorrectCoordinates())

    @Test
    fun stationHasCorrectCoordinates_IfBothCorrect_returnsTrue() =
        assert(Station("Уручча", 53.9453522, 27.687875).hasCorrectCoordinates())
}