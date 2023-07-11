package com.example.android_cryptoapp.domain

import javax.inject.Inject

class GetCoinInfoListUseCase @Inject constructor(
    private val repository: CoinRepository
    ) {

    operator fun invoke() = repository.getCoinInfoList()
}