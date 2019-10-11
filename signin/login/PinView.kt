package com.careem.mt.adma.ui.signin.login

import com.careem.mt.adma.ui.BaseView


interface PinView:BaseView {
    fun showProgress()
    fun hideProgress()
    fun onUserVerified(isTermsAndConditionsAccepted: Boolean)
    fun on203Response()
}
