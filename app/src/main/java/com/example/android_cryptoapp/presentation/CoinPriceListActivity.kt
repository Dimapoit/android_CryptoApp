package com.example.android_cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android_cryptoapp.R
import com.example.android_cryptoapp.presentation.CoinDetailActivity.Companion.newIntent
import com.example.android_cryptoapp.presentation.adapters.CoinInfoAdapter
import com.example.android_cryptoapp.databinding.ActivityCoinPriceListBinding
import com.example.android_cryptoapp.domain.CoinInfo

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        val adapter = CoinInfoAdapter(this)
        binding.rvCoinPriceList.adapter = adapter
        binding.rvCoinPriceList.itemAnimator = null
        viewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
        }
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                if (isOnePaneMode()) {
                    launchDetailActivity(coinPriceInfo.fromSymbol)
                } else {
                    launchDetailFragment(coinPriceInfo.fromSymbol)
                }
            }
        }
    }

    private fun isOnePaneMode() = binding.fragmentContainer == null

    private fun launchDetailActivity(fSym: String) {
        val intent = newIntent(this@CoinPriceListActivity, fSym)
        startActivity(intent)
    }

    private fun launchDetailFragment(fSym: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fSym))
            .addToBackStack(null)
            .commit()
    }
}


