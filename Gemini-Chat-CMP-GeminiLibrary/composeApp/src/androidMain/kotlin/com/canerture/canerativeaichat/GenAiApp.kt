package com.canerture.canerativeaichat

import android.app.Application
import com.canerture.canerativeaichat.di.initKoin

class GenAiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
