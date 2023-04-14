package com.example.android_cryptoapp.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.android_cryptoapp.R
import com.example.android_cryptoapp.databinding.ActivityCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    private val binding by lazy {
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }

        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getCoinInfo(fromSymbol).observe(this) {
            with(binding) {
                Picasso.get().load(it.imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivLogo)
                tvFromSymbolD.text = it.fromSymbol
                tvToSymbolD.text = it.toSymbol
                tvPriceD.text = it.price
                tvMinPriceD.text = it.lowDay
                tvMaxPriceD.text = it.highDay
                tvLastUpdateD.text = it.lastUpdate
                tvLastMarketD.text = it.lastMarket
            }
        }
    }

    companion object {
        const val EXTRA_FROM_SYMBOL = "fSym"
        const val EMPTY_SYMBOL = ""

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}