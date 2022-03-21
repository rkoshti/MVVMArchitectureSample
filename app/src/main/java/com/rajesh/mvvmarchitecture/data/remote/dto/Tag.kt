package com.rajesh.mvvmarchitecture.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val coin_counter: Int,
    val ico_counter: Int,
    val id: String,
    val name: String
)