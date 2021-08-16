package com.example.weatherr.model

import com.example.weatherr.model.data.Weather

sealed class Apstate{
    data class Succes(val weatherData: List<Weather>): Apstate()
    class Error(val error: Throwable): Apstate()
    object Loading: Apstate()
}
