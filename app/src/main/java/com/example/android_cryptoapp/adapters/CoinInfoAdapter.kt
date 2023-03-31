package com.example.android_cryptoapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_cryptoapp.R
import com.example.android_cryptoapp.adapters.CoinInfoAdapter.*
import com.example.android_cryptoapp.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) : RecyclerView.Adapter<CoinInfoViewHolder>() {

    var coinInfoList: List<CoinPriceInfo> = arrayListOf<CoinPriceInfo>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var onCoinClickListener: OnCoinClickListener? = null

    class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivLogoCoin = itemView.findViewById<ImageView>(R.id.ivLogoCoin)
        val tvSymbols = itemView.findViewById<TextView>(R.id.tvSymbols)
        val tvPrice = itemView.findViewById<TextView>(R.id.tvPrice)
        val tvLastUpdate = itemView.findViewById<TextView>(R.id.tvLastUpdate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        val symbolsTemplate = context.resources.getString(R.string.symbols_template)
        val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
        //Log.d("imageURL", coin.getFullImageUrl() )
        Picasso.get().load(coin.getFullImageUrl())
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.ivLogoCoin)
        holder.tvSymbols.text = String.format(symbolsTemplate, coin.fromSymbol, coin.toSymbol)
        holder.tvPrice.text = coin.price
        holder.tvLastUpdate.text = String.format(lastUpdateTemplate, coin.getFormattedTime())

        holder.itemView.setOnClickListener{
            onCoinClickListener?.onCoinClick(coin)
        }
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }

}