package com.example.android_cryptoapp.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.android_cryptoapp.R
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)

        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val ivLogo = findViewById<ImageView>(R.id.ivLogoCoinD)
        val tvFromSymbol = findViewById<TextView>(R.id.tvFromSymbolD)
        val tvToSymbol = findViewById<TextView>(R.id.tvToSymbolD)

        val tvPrice = findViewById<TextView>(R.id.tvPriceD)
        val tvMinPrice = findViewById<TextView>(R.id.tvMinPriceD)
        val tvMaxPrice = findViewById<TextView>(R.id.tvMaxPriceD)
        val tvLastUpdate = findViewById<TextView>(R.id.tvLastUpdateD)
        val tvLastMarket = findViewById<TextView>(R.id.tvLastMarketD)


        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getCoinInfo(fromSymbol).observe(this) {
            Picasso.get().load(it.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivLogo)
            tvFromSymbol.text = it.fromSymbol
            tvToSymbol.text = it.toSymbol
            tvPrice.text = it.price
            tvMinPrice.text = it.lowDay
            tvMaxPrice.text = it.highDay
            tvLastUpdate.text = it.lastUpdate
            tvLastMarket.text = it.lastMarket

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