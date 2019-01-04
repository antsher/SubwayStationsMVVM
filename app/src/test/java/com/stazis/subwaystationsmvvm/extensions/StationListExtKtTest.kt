package com.stazis.subwaystationsmvvm.extensions

import com.stazis.subwaystationsmvvm.model.entities.Station
import org.junit.Assert.assertEquals
import org.junit.Test

class StationListExtKtTest {

    @Test
    fun stationListCorrectStations_ifAllIncorrect_returnsEmptyList() {
        val listOfIncorrectStations = listOf(
            Station("Уручча", 0.0, 27.687875),
            Station("Уручча", 27.687875, 0.0)
        )
        assertEquals(listOfIncorrectStations.correctStations(), emptyList<Station>())
    }

    @Test
    fun stationListCorrectStations_ifSomeIncorrect_returnsCorrectList() {
        val listWithSomeIncorrectStations = listOf(
            Station("Уручча", 53.9453522, 27.687875),
            Station("Уручча", 0.0, 27.687875),
            Station("Уручча", 53.9453522, 27.687875),
            Station("Уручча", 53.9453522, 27.687875),
            Station("Уручча", 27.687875, 0.0)
        )
        val listOfCorrectStations = listOf(
            Station("Уручча", 53.9453522, 27.687875),
            Station("Уручча", 53.9453522, 27.687875),
            Station("Уручча", 53.9453522, 27.687875)
        )
        assertEquals(listWithSomeIncorrectStations.correctStations(), listOfCorrectStations)
    }

    @Test
    fun stationListCorrectStations_ifAllCorrect_returnsSameList() {
        val listOfCorrectStations = listOf(
            Station("Уручча", 53.9453522, 27.687875),
            Station("Уручча", 53.9453522, 27.687875),
            Station("Уручча", 53.9453522, 27.687875)
        )
        assertEquals(listOfCorrectStations.correctStations(), listOfCorrectStations)
    }
}