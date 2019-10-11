package com.careem.mt.adma.ui.signin.forgotpin

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.careem.mt.adma.R
import com.careem.mt.adma.ui.signin.BasePinFragment
import com.careem.mt.adma.ui.signin.login.PinPresenter
import com.careem.mt.adma.ui.signin.login.PinView
import com.careem.mt.adma.utils.AlertDialogs
import com.careem.mt.adma.utils.Constants
import com.careem.mt.adma.utils.CountDownTimer
import kotlinx.android.synthetic.main.fragment_forgot_pin_code.*
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val COUNTDOWN_START_VAL = 58L

class ForgotPinFragment : BasePinFragment(), ForgotPinView, PinView {
    @Inject
    lateinit var pinPresenter: PinPresenter

    @Inject
    lateinit var forgoPinPresenter: ForgotPinPresenter

    private lateinit var countDownTimer: CountDownTimer

    private lateinit var phoneNumber: String

    override fun getLayoutId() = R.layout.fragment_forgot_pin_code

    override fun initViews(parent: View, savedInstanceState: Bundle?) {
        super.initViews(parent, savedInstanceState)
        initEditTexts(etPin1, etPin2, etPin3, etPin4)
        if (arguments!!.containsKey(Constants.ARG_PHONE)) {
            phoneNumber = arguments!!.getString(Constants.ARG_PHONE)
        }
        tvYouWillReceiveSMS.text = getString(R.string.you_will_receive_a_sms_with_new_pin_on, phoneNumber)
        resendPinCode()
        forgoPinPresenter.attachView(this)
        pinPresenter.attachView(this)
    }

    private fun updateView() {
        tvResendPin.visibility = View.GONE
        tvYouWillReceiveSMS.visibility = View.GONE
        tvHeader.text = getString(R.string.enter_code_sent_on_phone, phoneNumber)
    }

    private fun setOnClickResendPin() {
        tvResendPin.text = getString(R.string.resend_pin)
        tvResendPin.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
        tvResendPin.setOnClickListener { resendPinCode() }
    }

    private fun resendPinCode() {
        forgoPinPresenter.getNewPinCode(phoneNumber)
        disableResendPin(Constants.START_TIME)
        startCountDown()
    }

    private fun startCountDown() {
        countDownTimer = object : CountDownTimer(COUNTDOWN_START_VAL, TimeUnit.SECONDS) {

            override fun onTick(tickValue: Long) {
                val millisUntilFinished = tickValue * Constants.TO_MILLI
                val text = String.format(Locale.getDefault(), "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % Constants.TO_MINUTE_OR_SEC,
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % Constants.TO_MINUTE_OR_SEC)
                disableResendPin(text)
            }

            override fun onFinish() {
                setOnClickResendPin()
            }
        }

        countDownTimer.start()
    }

    private fun disableResendPin(text: String) {
        tvResendPin.text = getString(R.string.resend_pin_in_time, text)
        tvResendPin.setTextColor(ContextCompat.getColor(activity!!, R.color.color_gray_text))
        tvResendPin.setOnClickListener(null)
    }

    override fun onDetach() {
        countDownTimer.cancel()
        pinPresenter.detachView()
        super.onDetach()
    }

    companion object {
        fun newInstance(param1: String): ForgotPinFragment {
            val fragment = ForgotPinFragment()
            val args = Bundle()
            args.putString(Constants.ARG_PHONE, param1)
            fragment.arguments = args
            return fragment
        }
    }

    override fun validateCode() {
        when (tvHeader.text) {
            getString(R.string.enter_code_sent_on_phone, phoneNumber) ->
                pinPresenter.verifyUser(pin)
            else ->
                pinPresenter.validatePinCode(phoneNumber, pin)

        }
    }

    override fun getTitle(): String = Constants.SPACE_STRING

    override fun onInvalidPhone(error: String) {
        AlertDialogs.showDialogWithCustomButtons(activity,
            getString(R.string.error), error, getString(R.string.ok),
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                fragmentHelper.clearFragmentBackStack()
            })
    }

    override fun showError(message: String) {
        AlertDialogs.showDialogWithCustomButtons(activity, getString(R.string.error),
            message, getString(R.string.ok))
    }

    override fun onUserVerified(isTermsAndConditionsAccepted: Boolean) {
        hideProgress()
        if (isTermsAndConditionsAccepted) navigateToMainActivity() else navigateToTermsAndUseScreen()
    }

    override fun on203Response() {
        resetFields()
        updateView()
    }

    override fun showProgress() {
        fragmentHelper.showLoadingDialog()
    }

    override fun hideProgress() {
        fragmentHelper.hideLoadingDialog()
    }
}
