package com.example.weatherr.model.repository

import com.example.weatherr.model.data.WeatherDTO
import com.example.weatherr.model.remote.RemoteDataSource
import retrofit2.Callback


class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {
    override fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: Callback<WeatherDTO>
    ) {
        remoteDataSource.getWeatherDetails(lat, lon, callback)
    }


}