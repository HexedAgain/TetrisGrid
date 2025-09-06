package com.example.tetrisgrid

import android.app.Application
import com.example.tetrisgrid.module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TetrisGridApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TetrisGridApplication)
            modules(appModule)
        }
    }
}