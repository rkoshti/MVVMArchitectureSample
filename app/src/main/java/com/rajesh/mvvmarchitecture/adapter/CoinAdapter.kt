package com.rajesh.mvvmarchitecture.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rajesh.mvvmarchitecture.data.remote.dto.CoinDto
import com.rajesh.mvvmarchitecture.databinding.ItemCoinListBinding
import javax.inject.Inject

class CoinAdapter @Inject constructor() : RecyclerView.Adapter<CoinAdapter.CoinViewHolder>(){

    class CoinViewHolder(val coinListBinding: ItemCoinListBinding) : RecyclerView.ViewHolder(coinListBinding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<CoinDto>() {
        override fun areItemsTheSame(oldItem: CoinDto, newItem: CoinDto): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CoinDto, newItem: CoinDto): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var coins: List<CoinDto>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding = ItemCoinListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding)
    }

    private var onItemClickListener: ((String) -> Unit)? = null
    fun setOnItemClickLister(listener: (String) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {

        val coin = coins[position]

        with(holder){
            with(coin){
                coinListBinding.tvCoinName.text = name
                coinListBinding.tvStatus.text = if(is_active) "Active" else "InActive"
            }

            itemView.apply {
                setOnClickListener {
                    onItemClickListener?.let { click ->
                        click(coin.id)
                    }
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return coins.size
    }
}