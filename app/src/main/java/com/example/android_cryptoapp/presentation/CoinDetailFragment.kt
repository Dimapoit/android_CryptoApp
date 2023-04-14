package com.example.android_cryptoapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_cryptoapp.R
import com.example.android_cryptoapp.databinding.FragmentCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailFragment : Fragment() {

    private lateinit var viewModel: CoinViewModel

    private  var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
    get() =   _binding ?: throw RuntimeException("FragmentCoinDetailBinding is null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getCoinInfo(getSymbol()).observe(viewLifecycleOwner) {
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

    private  fun getSymbol(): String {
        return requireArguments().getString(FROM_SYMBOL, EMPTY_SYMBOL)
    }

    companion object {
        const val FROM_SYMBOL = "fSym"
        const val EMPTY_SYMBOL = ""

        fun newInstance(fromSymbol: String): Fragment {
//            val fragment = CoinDetailFragment()
//            val args = Bundle()
//            args.putString(FROM_SYMBOL, fromSymbol)
//            fragment.arguments = args
//            return  fragment
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(FROM_SYMBOL, fromSymbol)
                }
            }
        }
    }
}