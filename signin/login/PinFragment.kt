package com.careem.mt.adma.ui.signin.login

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.careem.mt.adma.R
import com.careem.mt.adma.ui.signin.BasePinFragment
import com.careem.mt.adma.ui.signin.forgotpin.ForgotPinFragment
import com.careem.mt.adma.utils.AlertDialogs
import com.careem.mt.adma.utils.Constants
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_pin_code.*
import javax.inject.Inject

class PinFragment : BasePinFragment(), PinView {
    @Inject
    lateinit var pinPresenter: PinPresenter

    private lateinit var phoneNo: String

    override fun showProgress() {
        fragmentHelper.showLoadingDialog()
    }

    override fun hideProgress() {
        fragmentHelper.hideLoadingDialog()
    }

    override fun onUserVerified(isTermsAndConditionsAccepted: Boolean) {
        hideProgress()
        if (isTermsAndConditionsAccepted) navigateToMainActivity() else navigateToTermsAndUseScreen()
    }

    override fun showError(message: String) {
        resetFields()
        AlertDialogs.showDialogWithCustomButtons(activity, getString(R.string.error), message, getString(R.string.ok))
    }

    override fun on203Response() {
        resetFields()
        updateView()
    }

    private fun updateView() {
        tvForgotPin.visibility = View.GONE
        tvHeader.text = getString(R.string.enter_code_sent_on_phone, phoneNo)
    }

    override fun getLayoutId() = R.layout.fragment_pin_code

    override fun initViews(parent: View, savedInstanceState: Bundle?) {
        super.initViews(parent, savedInstanceState)
        initEditTexts(etPin1, etPin2, etPin3, etPin4)
        if (arguments!!.containsKey(Constants.ARG_PHONE)) {
            phoneNo = arguments!!.getString(Constants.ARG_PHONE)
        }
        tvForgotPin.setOnClickListener { showGenerateNewPinDialogue() }
        pinPresenter.attachView(this)
    }

    private fun showGenerateNewPinDialogue() {
        AlertDialogs.showDialogWithCustomButtons(activity,
            getString(R.string.generate_new_pin_title),
            getString(R.string.generate_new_pin_message, phoneNo),
            getString(R.string.confirm),
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                navigateToForgotPinFragment()
            },
            true)
    }

    private fun navigateToForgotPinFragment() {
        fragmentHelper.addFragment(ForgotPinFragment.newInstance(phoneNo), false, true)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun newInstance(param1: String): PinFragment {
            val fragment = PinFragment()
            val args = Bundle()
            args.putString(Constants.ARG_PHONE, param1)
            fragment.arguments = args
            return fragment
        }
    }

    override fun validateCode() {
        when (tvHeader.text) {
            getString(R.string.enter_your_pin_code) ->
                pinPresenter.validatePinCode(phoneNo, pin)
            else ->
                pinPresenter.verifyUser(pin)

        }
    }

    override fun getTitle(): String = Constants.SPACE_STRING

    override fun onDetach() {
        super.onDetach()
        pinPresenter.detachView()
    }
}
