package com.careem.mt.adma.ui.signin.forgotpin

import com.careem.mt.adma.ui.BaseView

interface ForgotPinView : BaseView {
    fun showProgress()
    fun hideProgress()
    fun onInvalidPhone(error: String)
}
