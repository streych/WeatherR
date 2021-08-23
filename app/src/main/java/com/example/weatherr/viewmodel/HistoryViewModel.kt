package com.example.weatherr.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherr.app.App.Companion.getHistoryDao
import com.example.weatherr.app.LocalRepositoryImpl
import kotlinx.coroutines.InternalCoroutinesApi
import java.lang.Thread.sleep

@InternalCoroutinesApi
class HistoryViewModel(
    val historyLiveData: MutableLiveData<Apstate> = MutableLiveData(),
    private val historyRepository: LocalRepositoryImpl =  LocalRepositoryImpl(getHistoryDao())
 ): ViewModel() {
     fun getAllHistory(){
         historyLiveData.value = Apstate.Loading
         Thread{
             sleep(1000)
             historyLiveData.postValue(Apstate.Succes(historyRepository.getAllHistory()))
         }.start()


     }
}