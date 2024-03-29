package com.example.android_cryptoapp.di

import android.app.Application
import com.example.android_cryptoapp.presentation.CoinDetailFragment
import com.example.android_cryptoapp.presentation.CoinPriceListActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(activity: CoinPriceListActivity)

    fun inject(fragment: CoinDetailFragment)

    @Component.Factory
    interface Factory{

        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}