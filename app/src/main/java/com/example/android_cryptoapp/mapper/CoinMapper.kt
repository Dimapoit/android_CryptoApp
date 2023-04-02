package com.example.android_cryptoapp.mapper

import com.example.android_cryptoapp.data.database.CoinInfoDbModel
import com.example.android_cryptoapp.data.network.model.CoinInfoDto
import com.example.android_cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.example.android_cryptoapp.data.network.model.CoinNamesListDto
import com.example.android_cryptoapp.domain.CoinInfo
import com.google.gson.Gson

class CoinMapper {

    fun mapDaoToDbModel(coinInfoDto: CoinInfoDto) = CoinInfoDbModel(
        fromSymbol = coinInfoDto.fromSymbol,
        toSymbol = coinInfoDto.toSymbol,
        price = coinInfoDto.price,
        lastUpdate = coinInfoDto.lastUpdate,
        highDay = coinInfoDto.highDay,
        lowDay = coinInfoDto.lowDay,
        lastMarket = coinInfoDto.lastMarket,
        imageUrl = coinInfoDto.imageUrl
    )

    fun mapJsonContainerToListCoinDto(jsonContainerDto: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainerDto.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for(coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map { it.coinNameDto?.name }?.joinToString { "," } ?: ""
    }

    fun dbModelToEntity(coinInfoDbModel: CoinInfoDbModel): CoinInfo = CoinInfo(
        fromSymbol = coinInfoDbModel.fromSymbol,
        toSymbol = coinInfoDbModel.toSymbol,
        price = coinInfoDbModel.price,
        lastUpdate = coinInfoDbModel.lastUpdate,
        highDay = coinInfoDbModel.highDay,
        lowDay = coinInfoDbModel.lowDay,
        lastMarket = coinInfoDbModel.lastMarket,
        imageUrl = coinInfoDbModel.imageUrl
    )
}

