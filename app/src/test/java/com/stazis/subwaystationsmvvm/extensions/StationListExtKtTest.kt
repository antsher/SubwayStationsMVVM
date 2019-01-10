package com.stazis.subwaystationsmvvm.extensions

import com.stazis.subwaystationsmvvm.model.entities.Station
import org.junit.Assert.assertEquals
import org.junit.Test

class StationListExtKtTest {

    @Test
    fun `getCorrectStations IfAllIncorrect ReturnsEmptyList`() {
        val listOfIncorrectStations = listOf(
            Station("Уручча", 0.0, 27.687875),
            Station("Уручча", 27.687875, 0.0)
        )
        assertEquals(listOfIncorrectStations.getCorrectStations(), emptyList<Station>())
    }

    @Test
    fun `getCorrectStations IfSomeIncorrect ReturnsCorrectList`() {
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
        assertEquals(listWithSomeIncorrectStations.getCorrectStations(), listOfCorrectStations)
    }

    @Test
    fun `getCorrectStations IfAllCorrect ReturnsSameList`() {
        val listOfCorrectStations = listOf(
            Station("Уручча", 53.9453522, 27.687875),
            Station("Уручча", 53.9453522, 27.687875),
            Station("Уручча", 53.9453522, 27.687875)
        )
        assertEquals(listOfCorrectStations.getCorrectStations(), listOfCorrectStations)
    }
}