package com.rajesh.mvvmarchitecture.domain.model

import com.rajesh.mvvmarchitecture.data.remote.dto.Team


data class CoinDetail(
    val description: String,
    val coinId: String,
    val is_active: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val tags: List<String>,
    val team: List<Team>
)
