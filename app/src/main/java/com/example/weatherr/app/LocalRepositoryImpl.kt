package com.example.weatherr.app

import com.example.weatherr.model.data.Weather
import com.example.weatherr.model.data.convertHistoryEntityToWeather
import com.example.weatherr.model.data.convertToWeatherToEntity
import com.example.weatherr.room.HistoryDao

class LocalRepositoryImpl(private val localDataSource: HistoryDao) : LocalRepository {

    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertToWeatherToEntity(weather))
    }
}