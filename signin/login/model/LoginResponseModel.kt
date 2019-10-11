package com.careem.mt.adma.ui.signin.login.model

import com.google.gson.annotations.SerializedName


data class LoginResponseModel(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("expires_in") val expiresIn: Int
)
