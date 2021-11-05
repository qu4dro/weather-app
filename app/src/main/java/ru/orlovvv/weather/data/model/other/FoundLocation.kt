package ru.orlovvv.weather.data.model.other

data class FoundLocation(
    var country: String,
    var id: Int,
    var lat: Double,
    var lon: Double,
    var name: String,
    var region: String,
    var url: String
)