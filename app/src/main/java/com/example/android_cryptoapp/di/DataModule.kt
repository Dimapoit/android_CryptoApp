package com.example.android_cryptoapp.di

import android.app.Application
import com.example.android_cryptoapp.data.database.AppDatabase
import com.example.android_cryptoapp.data.database.CoinPriceInfoDao
import com.example.android_cryptoapp.data.repository.CoinRepositoryImpl
import com.example.android_cryptoapp.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object {
        @Provides
        fun provideCoinInfoDao(
            application: Application
        ): CoinPriceInfoDao {
            return AppDatabase.getInstance(application).coinPriceInfoDao()
        }
    }
}