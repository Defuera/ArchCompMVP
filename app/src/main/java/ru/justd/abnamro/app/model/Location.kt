package ru.justd.abnamro.app.model

data class Location(
        val address: String,
        val city: String,
        val state: String,
        val country: String,
        val lat: Double,
        val lng: Double
)