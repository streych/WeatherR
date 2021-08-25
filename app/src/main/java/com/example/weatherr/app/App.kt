package com.example.weatherr.app

import android.app.Application
import androidx.room.Room
import com.example.weatherr.room.HistoryDao
import com.example.weatherr.room.HistoryDataBase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: App? = null
        private var db: HistoryDataBase? = null
        private const val DB_NAME = "History.db"

        @InternalCoroutinesApi
        fun getHistoryDao(): HistoryDao {

                if (db == null) {
                    synchronized(HistoryDataBase::class.java) {
                        if (db == null) {
                            if (appInstance == null) throw
                            IllegalStateException("Application is null while creating DataBase")
                            db = Room.databaseBuilder(
                                appInstance!!.applicationContext,
                                HistoryDataBase::class.java,
                                DB_NAME
                            )
                                .allowMainThreadQueries()
                                .build()

                        }
                    }
                }
            return db!!.historyDao()
        }
    }
}