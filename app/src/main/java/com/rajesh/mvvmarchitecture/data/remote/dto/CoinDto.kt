package com.rajesh.mvvmarchitecture.data.remote.dto

import com.rajesh.mvvmarchitecture.domain.model.Coin
import kotlinx.serialization.Serializable

@Serializable
data class CoinDto(
    val id: String,
    val is_active: Boolean,
    val is_new: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String
)

fun CoinDto.toCoin() : Coin {

    return Coin(
        id = id,
        is_active = is_active,
        name = name,
        rank = rank,
        symbol = symbol
    )
}
