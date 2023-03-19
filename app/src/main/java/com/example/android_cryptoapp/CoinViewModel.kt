package com.example.android_cryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.android_cryptoapp.api.ApiFactory
import com.example.android_cryptoapp.database.AppDatabase
import com.example.android_cryptoapp.pojo.CoinPriceInfo
import com.example.android_cryptoapp.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)

    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

//    fun loadData() {
//        val disposable = ApiFactory.apiService.getTopCoinsInfo()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                val a = it.data?.map { it.coinInfo?.name }?.joinToString(",")
//
//                Log.d("TEST_OF_LOADING_DATA", a.toString())
//            }, {
//                Log.d("TEST_OF_LOADING_DATA", "Failure ${it.message}")
//            })
//        compositeDisposable.add(disposable)
//    }


    fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") }
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRawData(it) }
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d("TEST_OF_LOADING_DATA", "Success $it")
            }, {
                Log.d("TEST_OF_LOADING_DATA", "Failure ${it.message}")
            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRawData(coinPriceInfoRawData: CoinPriceInfoRawData): List<CoinPriceInfo>{
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject
        if (jsonObject == null) return result
        val coinKeySet = jsonObject.keySet()
        for(coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            //Log.d("TEST_OF_LOADING_DATA", "currencyKeySet $currencyKeySet")
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}