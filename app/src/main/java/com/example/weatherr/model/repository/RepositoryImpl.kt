package com.example.weatherr.model.repository

import com.example.weatherr.model.data.Weather
import com.example.weatherr.model.data.getRussianCities

class RepositoryImpl: Repository {
    override fun getWeatherFromServer(): List<Weather> = getRussianCities()


    override fun getWeatherFromLocalStorage(): List<Weather> = getRussianCities()

}