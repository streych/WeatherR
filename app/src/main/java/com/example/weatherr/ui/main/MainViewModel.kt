package com.example.weatherr.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherr.model.Apstate
import com.example.weatherr.model.repository.Repository
import com.example.weatherr.model.repository.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(private val repository: Repository = RepositoryImpl()) :
    ViewModel() {

    private  val liveDataToObserve: MutableLiveData<Apstate> = MutableLiveData()
    fun getLiveData() = liveDataToObserve
    fun getWeather() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = Apstate.Loading
        Thread{
            sleep(1000);
            liveDataToObserve.postValue(Apstate.Succes(repository.getWeatherFromLocalStorage()))
        }.start()
    }
}