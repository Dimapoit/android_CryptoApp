package com.example.android_cryptoapp.presentation

import android.app.Application
import com.example.android_cryptoapp.di.DaggerAppComponent


class CoinApp : Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}