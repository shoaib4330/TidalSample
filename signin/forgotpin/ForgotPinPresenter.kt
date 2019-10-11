package com.careem.mt.adma.ui.signin.forgotpin

import com.careem.mt.adma.backend.AuthenticationNetworkHelper
import com.careem.mt.adma.backend.ConsumerGateway
import com.careem.mt.adma.backend.ServiceCallback
import com.careem.mt.adma.ui.BasePresenter
import javax.inject.Inject

private const val NOT_FOUND = "not found"
private const val TIME_OUT = "timeout"

class ForgotPinPresenter @Inject constructor(val consumerGateway: ConsumerGateway,
                                             val authenticationNetworkHelper: AuthenticationNetworkHelper)
    : BasePresenter<ForgotPinView>() {

    fun getNewPinCode(phoneNumber: String) {
        view?.showProgress()
        authenticationNetworkHelper.serviceCall(consumerGateway.sendPin(phoneNumber),
            object : ServiceCallback<Any> {
                override fun onSuccess(response: Any) {
                    view?.hideProgress()
                }

                override fun onFailure(error: String, statusCode: Int, errorCodeString: String) {
                    handleGetNewPinFailureResponse(error)
                }
            })
    }

    private fun handleGetNewPinFailureResponse(error: String) {
        if (error.contains(NOT_FOUND, true))
            view?.onInvalidPhone(error)
        else {
            if (error.contains(TIME_OUT, true))
                view?.hideProgress()
            else
                view?.showError(error)
        }
        view?.hideProgress()
    }

    override fun detachView() {
        super.detachView()
        authenticationNetworkHelper.dispose()
    }
}
