package com.example.android_cryptoapp.domain

class GetCoinInfoUseCase(private  val repository: CoinRepository) {

    operator fun invoke(fromSymbol: String ) = repository.getCoinInfo(fromSymbol)
}