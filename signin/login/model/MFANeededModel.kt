package com.careem.mt.adma.ui.signin.login.model

data class MFANeededModel(val data: Data)

data class Data(val entity: String, val identity: Int, val phoneCodeResponse: PhoneCodeResponse, val token: String)

data class PhoneCodeResponse(val status: String, val retryTime: Int, val expiryTime: Int)
