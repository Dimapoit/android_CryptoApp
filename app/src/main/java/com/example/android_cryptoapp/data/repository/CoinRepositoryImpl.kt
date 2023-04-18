package com.example.android_cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
//import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.android_cryptoapp.data.database.AppDatabase
import com.example.android_cryptoapp.data.workers.RefreshDataWorker
import com.example.android_cryptoapp.domain.CoinInfo
import com.example.android_cryptoapp.domain.CoinRepository
import com.example.android_cryptoapp.mapper.CoinMapper

class CoinRepositoryImpl(private val application: Application) : CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper = CoinMapper()


    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
//        return Transformations.map(coinInfoDao.getPriceList()) {
//            it.map { mapper.dbModelToEntity(it) }
//        }
        return coinInfoDao.getPriceList().map {
            it.map {
                mapper.dbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
//        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)) {
//            mapper.dbModelToEntity(it)
//        }
        return coinInfoDao.getPriceInfoAboutCoin(fromSymbol).map {
            mapper.dbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }
}