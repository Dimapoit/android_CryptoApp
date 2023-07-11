package com.example.android_cryptoapp.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_cryptoapp.domain.GetCoinInfoListUseCase
import com.example.android_cryptoapp.domain.GetCoinInfoUseCase
import com.example.android_cryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {
    val coinInfoList = getCoinInfoListUseCase()

    fun getCoinInfo(fSym: String) = getCoinInfoUseCase(fSym)

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }

    }
}