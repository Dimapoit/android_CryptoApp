package com.example.android_cryptoapp.domain

import javax.inject.Inject

class LoadDataUseCase @Inject constructor(
    private val coinRepository: CoinRepository
    ) {

    suspend operator fun invoke() = coinRepository.loadData()
}