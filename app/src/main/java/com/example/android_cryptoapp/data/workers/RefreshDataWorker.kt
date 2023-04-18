package com.example.android_cryptoapp.data.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.android_cryptoapp.data.database.AppDatabase
import com.example.android_cryptoapp.data.network.ApiFactory
import com.example.android_cryptoapp.mapper.CoinMapper
import kotlinx.coroutines.delay

class RefreshDataWorker(context: Context, parameters: WorkerParameters): CoroutineWorker(context, parameters) {

    private val coinInfoDao = AppDatabase.getInstance(context).coinPriceInfoDao()
    private val apiService = ApiFactory.apiService
    private val mapper = CoinMapper()

    override suspend fun doWork(): Result {
        while (true) {
            try {
                Log.d("doWork", "doWork")
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                Log.d("doWork", topCoins.toString())
                val fSyms = mapper.mapNamesListToString(topCoins)
                Log.d("doWork", fSyms.toString())
                val jsonContainer = apiService.getFullPriceList(fSyms = fSyms)
                val coinInfoDtoList = mapper.mapJsonContainerToListCoinDto(jsonContainer)
                Log.d("doWork", coinInfoDtoList.toString())
                val dbModelList = coinInfoDtoList.map { mapper.mapDaoToDbModel(it) }
                coinInfoDao.insertPriceList(dbModelList)
            } catch (e: Exception) {
                Log.d("doWorkCatch", e.message.toString())
            }
            delay(10000)
        }
    }

    companion object {

        const val NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }
}


