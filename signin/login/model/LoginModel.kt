package com.careem.mt.adma.ui.signin.login.model

data class LoginModel(val admaDeviceId: String?, val admaVersion: String?, val admaVersionNumber: Int?,
                      val bootLoader: String?, val buildRelease: String?, val device: String?,
                      val deviceBoard: String?, val deviceBrand: String?, val deviceImei: String?,
                      val deviceSoftwareVersion: String?, val display: String?, val driverPhone: String?,
                      val fingerPrint: String?, val googlePlayServicesVersion: Int?, val hardware: String?,
                      val lastLoggedInDriverId: Int?, val manufacturer: String?, val model: String?,
                      val networkCountryIso: String?, val networkOperator: String?,
                      val networkOperatorName: String?, val networkType: String?, val pinCode: String?,
                      val product: String?, val pushyDeviceId: String?, val pushyEnterpriseDeviceId: String?,
                      val sdkInt: Int, val serial: String?, val simCountryIso: String?, val simId: String?,
                      val simOperator: String?, val simOperatorName: String?, val simState: String?
)
