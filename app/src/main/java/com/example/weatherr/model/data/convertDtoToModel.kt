package com.example.weatherr.model.data

import com.example.weatherr.room.HistoryEntity

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather>{
    return entityList.map {
        Weather(City(it.city, 0.0, 0.0), it.temperature, 0, it.condition)
    }
}

fun convertToWeatherToEntity(weather: Weather): HistoryEntity{
    return HistoryEntity(0, weather.city.city, weather.temperature, weather.condition)
}

