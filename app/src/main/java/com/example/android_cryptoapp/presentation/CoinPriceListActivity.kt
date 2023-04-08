package com.example.android_cryptoapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.android_cryptoapp.R
import com.example.android_cryptoapp.presentation.CoinDetailActivity.Companion.newIntent
import com.example.android_cryptoapp.presentation.adapters.CoinInfoAdapter
import com.example.android_cryptoapp.data.network.model.CoinInfoDto
import com.example.android_cryptoapp.domain.CoinInfo

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        val adapter = CoinInfoAdapter(this)

        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                val intent = newIntent(this@CoinPriceListActivity, coinPriceInfo.fromSymbol)
                startActivity(intent)
            }

        }
        val rvCoinPriceList = findViewById<RecyclerView>(R.id.rv_coin_price_list)
        rvCoinPriceList.adapter = adapter
        viewModel.coinInfoList.observe(this) {
            Log.d("it", it.toString())
            adapter.coinInfoList = it
        }
    }
}


