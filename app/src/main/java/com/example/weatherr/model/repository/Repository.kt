package com.example.weatherr.model.repository

import com.example.weatherr.model.data.Weather

interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorage(): Weather
}