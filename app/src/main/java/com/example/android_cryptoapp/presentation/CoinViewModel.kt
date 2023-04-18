package com.example.android_cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_cryptoapp.data.repository.CoinRepositoryImpl
import com.example.android_cryptoapp.domain.GetCoinInfoListUseCase
import com.example.android_cryptoapp.domain.GetCoinInfoUseCase
import com.example.android_cryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =  CoinRepositoryImpl(application)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private  val loadDataUseCase = LoadDataUseCase(repository)


    val coinInfoList = getCoinInfoListUseCase()

    fun getCoinInfo(fSym:String) = getCoinInfoUseCase(fSym)

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }

    }
}