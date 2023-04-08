package com.example.android_cryptoapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.android_cryptoapp.domain.CoinInfo

class CoinInfoDiffUtilCallback: DiffUtil.ItemCallback<CoinInfo>() {
    override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return  oldItem == newItem
    }
}