package com.example.weatherr.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherr.app.App.Companion.getHistoryDao
import com.example.weatherr.app.LocalRepository
import com.example.weatherr.app.LocalRepositoryImpl
import com.example.weatherr.model.data.Weather
import com.example.weatherr.model.data.WeatherDTO
import com.example.weatherr.model.data.convertDtoToModel
import com.example.weatherr.model.remote.RemoteDataSource
import com.example.weatherr.model.repository.DetailsRepositoryImpl
import kotlinx.coroutines.InternalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val CORRUPTED_DATA = "Неполные данные"

@InternalCoroutinesApi
class DetailsViewModel(
    private val detailsLiveData: MutableLiveData<Apstate> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepositoryImpl = DetailsRepositoryImpl(
        RemoteDataSource()
    ),
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {
    fun getLiveData() = detailsLiveData
    fun getWeatherFromRemoteSource(lat: Double, lon: Double) {
        detailsLiveData.value = Apstate.Loading
        detailsRepositoryImpl.getWeatherDetailsFromServer(lat, lon, callBack)
    }

    private val callBack = object : Callback<WeatherDTO> {
        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            val serResponse: WeatherDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serResponse != null) {
                    checkResponse(serResponse)
                } else {
                    Apstate.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
            Apstate.Error(Throwable(CORRUPTED_DATA))
        }

    }

    private fun checkResponse(serverResponse: WeatherDTO): Apstate {
        val fact = serverResponse.fact
        return if (fact == null || fact.temp == null || fact.feels_like == null || fact.condition.isNullOrEmpty()) {
            Apstate.Error(Throwable(CORRUPTED_DATA))
        } else {
            Apstate.Succes(convertDtoToModel(serverResponse))
        }
    }

    fun saveCityToDb(weather: Weather) {
        historyRepository.saveEntity(weather)
    }
}

