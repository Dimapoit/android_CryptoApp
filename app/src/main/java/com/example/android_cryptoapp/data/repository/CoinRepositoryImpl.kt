package com.example.android_cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.android_cryptoapp.data.database.AppDatabase
import com.example.android_cryptoapp.data.network.ApiFactory
import com.example.android_cryptoapp.domain.CoinInfo
import com.example.android_cryptoapp.domain.CoinRepository
import com.example.android_cryptoapp.mapper.CoinMapper
import kotlinx.coroutines.delay

class CoinRepositoryImpl(application: Application) : CoinRepository {

    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val apiService = ApiFactory.apiService
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
        val topCoins = apiService.getTopCoinsInfo(limit = 50)
        val fSyms = mapper.mapNamesListToString(topCoins)
        val jsonContainer = apiService.getFullPriceList(fSyms = fSyms)
        val coinInfoDtoList = mapper.mapJsonContainerToListCoinDto(jsonContainer)
        val dbModelList = coinInfoDtoList.map { mapper.mapDaoToDbModel(it) }
        coinInfoDao.insertPriceList(dbModelList)
        delay(10000)
    }
}