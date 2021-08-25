package com.example.weatherr.app

import com.example.weatherr.model.data.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
}