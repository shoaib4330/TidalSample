package com.careem.mt.adma.ui.signin.login

import com.careem.mt.adma.backend.AuthenticationNetworkHelper
import com.careem.mt.adma.backend.ConsumerGateway
import com.careem.mt.adma.backend.ServiceCallback
import com.careem.mt.adma.data.LoginManager
import com.careem.mt.adma.ui.BasePresenter
import com.careem.mt.adma.ui.signin.login.model.*
import com.careem.mt.adma.utils.ApiErrorCodes
import com.careem.mt.adma.utils.Constants
import com.careem.mt.adma.utils.DeviceUtils
import com.careem.mt.adma.utils.Util
import com.google.gson.Gson
import com.google.gson.JsonObject
import javax.inject.Inject


class PinPresenter @Inject constructor(val loginManager: LoginManager,
                                       val consumerGateway: ConsumerGateway,
                                       val authenticationNetworkHelper: AuthenticationNetworkHelper,
                                       val deviceUtils: DeviceUtils) : BasePresenter<PinView>() {
    var isInNetworkCall = false
    fun validatePinCode(phoneNo: String, pin: String,
                        googlePlayVersion: Int =
                            Util.getApplicationVersion(Constants.GOOGLE_PLAY_PACKAGE_NAME)) {
        if (isInNetworkCall)
            return
        isInNetworkCall = true
        val loginModel = createLoginModel(phoneNo, pin, googlePlayVersion)
        view?.showProgress()
        authenticationNetworkHelper.serviceCall(consumerGateway.login(loginModel),
            object : ServiceCallback<JsonObject> {
                override fun onSuccess(response: JsonObject) {
                    handleVerifyUserSuccessResponse(response)
                }

                override fun onFailure(error: String, statusCode: Int, errorCodeString: String) {
                    handleValidatePinFailureResponse(statusCode, error)
                }

            })
    }

    private fun handleValidatePinFailureResponse(statusCode: Int, error: String) {
        isInNetworkCall = false
        when (statusCode) {
            ApiErrorCodes.MFA_NEEDED ->
                onProcess203Response(error)
            ApiErrorCodes.WRONG_CREDENTIALS ->
                onLoginFailed(error)
            else -> {
                showError(error)
            }
        }
    }

    private fun onLoginFailed(mesage: String) {
        showError(mesage)
    }

    private fun onProcess203Response(response: String) {
        val mfaModel = Gson().fromJson(response, MFANeededModel::class.java)
        loginManager.storeMFAToken(mfaModel.data.token)
        view?.hideProgress()
        view?.on203Response()
    }

    fun verifyUser(code: String) {
        val verifyModel = getVerifyModel(code)
        verifyUser(verifyModel)
    }

    private fun verifyUser(verifyModel: VerifyModel) {
        if (isInNetworkCall)
            return
        isInNetworkCall = true
        view?.showProgress()
        authenticationNetworkHelper.serviceCall(consumerGateway.verify(verifyModel),
            object : ServiceCallback<JsonObject> {
                override fun onSuccess(response: JsonObject) {
                    handleVerifyUserSuccessResponse(response)
                }

                override fun onFailure(error: String, statusCode: Int, errorCodeString: String) {
                    handleVerifyUserFailureResponse(error)
                }
            })
    }

    private fun handleVerifyUserFailureResponse(error: String) {
        isInNetworkCall = false
        showError(error)
    }

    private fun handleVerifyUserSuccessResponse(response: JsonObject) {
        isInNetworkCall = false
        loginManager.removeMFAToken()
        val loginResponseModel = Gson().fromJson<LoginResponseModel>(response, LoginResponseModel::class.java)
        onHandle200Response(loginResponseModel)
    }

    private fun showError(error: String) {
        view?.hideProgress()
        view?.showError(error)
    }

    private fun getVerifyModel(code: String): VerifyModel {
        return VerifyModel(deviceUtils.imei!!, code, loginManager.getMFAToken())
    }

    private fun onHandle200Response(response: LoginResponseModel) {
        loginManager.storeUserAccessToken(response.accessToken)
        getCaptainInfo(response)
    }

    private fun getCaptainInfo(loginResponseModel: LoginResponseModel) {
        if (isInNetworkCall)
            return
        isInNetworkCall = true
        view?.showProgress()
        authenticationNetworkHelper.serviceCall(consumerGateway.info(),
            object : ServiceCallback<UserModel> {
                override fun onSuccess(response: UserModel) {
                    handleGetCaptainInfoSuccessResponse(response, loginResponseModel)
                }

                override fun onFailure(error: String, statusCode: Int, errorCodeString: String) {
                    handleGetCaptainInfoFailureResponse(error)
                }
            })
    }

    private fun handleGetCaptainInfoFailureResponse(error: String) {
        isInNetworkCall = false
        loginManager.logout()
        showError(error)
    }

    private fun handleGetCaptainInfoSuccessResponse(response: UserModel, loginResponseModel: LoginResponseModel) {
        isInNetworkCall = false
        storeUserData(response, loginResponseModel)
        view?.onUserVerified(loginManager.isUserAcceptedTermsAndConditions())
    }

    private fun storeUserData(response: UserModel, loginResponseModel: LoginResponseModel) {
        loginManager.storeUserId(response.user.userId)
        loginManager.storeRefreshToken(loginResponseModel.refreshToken)
        loginManager.storeUserAccessToken(loginResponseModel.accessToken)
    }

    private fun createLoginModel(phoneNo: String, pin: String, googlePlayVersion: Int): LoginModel {
        return LoginModelBuilder()
            .setDriverPhone(phoneNo)
            .setPinCode(pin)
            .setAdmaDeviceId(Constants.EMPTY_STRING)
            .setPushyDeviceId(Constants.EMPTY_STRING)
            .setPushyEnterpriseDeviceId(Constants.EMPTY_STRING)
            .setAdmaVersion(Constants.ADMA_VERSION)
            .setAdmaVersionNumber(Constants.INTEGER_ADMA_VERSION)
            .setDeviceImei(deviceUtils.imei!!)
            .setSimId(deviceUtils.imsi)
            .setGooglePlayServicesVersion(googlePlayVersion)
            .setDeviceSoftwareVersion(deviceUtils.deviceSoftwareVersion)
            .setLineNumber(deviceUtils.lineNumber)
            .setNetworkCountryIso(deviceUtils.networkCountryIso)
            .setNetworkOperator(deviceUtils.networkOperator)
            .setNetworkOperatorName(deviceUtils.networkOperatorName)
            .setNetworkType(deviceUtils.networkType)
            .setSimCountryIso(deviceUtils.simCountryIso)
            .setSimOperator(deviceUtils.simOperator)
            .setSimOperatorName(deviceUtils.simOperatorName)
            .setSimSerialNumber(deviceUtils.simSerialNumber)
            .setSimState(deviceUtils.simState)
            .setDeviceBoard(deviceUtils.board)
            .setBootLoader(deviceUtils.bootloader)
            .setDeviceBrand(deviceUtils.brand)
            .setDevice(deviceUtils.device)
            .setDisplay(deviceUtils.display)
            .setFingerPrint(deviceUtils.fingerPrint)
            .setHardware(deviceUtils.hardware)
            .setManufacturer(deviceUtils.manufacturer)
            .setModel(deviceUtils.model)
            .setProduct(deviceUtils.product)
            .setSerial(deviceUtils.serial)
            .setBuildRelease(deviceUtils.buildRelease)
            .setSdkInt(deviceUtils.sdkInt)
            .setLastLoggedInDriverId(-1)
            .create()
    }

    override fun detachView() {
        authenticationNetworkHelper.dispose()
        super.detachView()
    }
}
