package com.careem.mt.adma.ui.signin

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.careem.mt.adma.R
import com.careem.mt.adma.ui.BaseFragment
import com.careem.mt.adma.ui.signin.login.PinFragment
import com.careem.mt.adma.utils.Constants
import com.careem.mt.adma.utils.DeviceUtils
import com.careem.mt.adma.utils.Util
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_phone_number.*
import javax.inject.Inject

private const val PERMISSION_REQUEST_PHONE_STATE = 1010
private const val PERMISSION_REQUEST_PHONE_STATE_AFTER_PHONE_ENTERED = 2020

class PhoneNumberFragment : BaseFragment(), TextWatcher {
    @Inject
    lateinit var deviceUtils: DeviceUtils

    override fun getLayoutId() = R.layout.fragment_phone_number

    override fun initViews(parent: View, savedInstanceState: Bundle?) {
        super.initViews(parent, savedInstanceState)
        AndroidSupportInjection.inject(this)
        if (!isAlreadyHavePhoneStatePermission()) {
            requestPhoneStatePermission(PERMISSION_REQUEST_PHONE_STATE)
        }
        initView()


    }

    private fun initView() {
        tilPhoneNumber.editText?.addTextChangedListener(this)
        tilPhoneNumber.editText?.setText(deviceUtils.countryPhoneCode)
        tilPhoneNumber.editText?.setSelection(deviceUtils.countryPhoneCode.length)
        tvNext.setOnClickListener { onNextClick() }
        ivClearPhoneNumber.setOnClickListener {
            tilPhoneNumber.editText?.setText(Constants.EMPTY_STRING)
        }
    }

    private fun onNextClick() {
        if (validateNumber()) {
            tilPhoneNumber.error = getString(R.string.error_phone_not_valid)
            tilPhoneNumber.isErrorEnabled = true
            ivClearPhoneNumber.setPadding(ivClearPhoneNumber.paddingLeft,
                ivClearPhoneNumber.paddingTop,
                ivClearPhoneNumber.paddingRight,
                ivClearPhoneNumber.paddingBottom * 2)
        } else if (!isAlreadyHavePhoneStatePermission()) {
            requestPhoneStatePermission(PERMISSION_REQUEST_PHONE_STATE_AFTER_PHONE_ENTERED)
        } else
            launchPinFragment()
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s?.trim()?.length!! > 0) {
            ivClearPhoneNumber.visibility = View.VISIBLE
        } else
            ivClearPhoneNumber.visibility = View.INVISIBLE
        tilPhoneNumber.isErrorEnabled = false
        ivClearPhoneNumber.setPadding(ivClearPhoneNumber.paddingLeft,
            ivClearPhoneNumber.paddingTop,
            ivClearPhoneNumber.paddingRight,
            0)

    }

    private fun validateNumber(): Boolean {
        return tilPhoneNumber.editText?.text?.length!! < Constants.PHONE_NUMBER_MIN_LENGTH
    }

    private fun launchPinFragment() {
        fragmentHelper.addFragment(PinFragment.newInstance(etPhoneNumber.text.toString()), false, true)
    }

    private fun isAlreadyHavePhoneStatePermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_PHONE_STATE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPhoneStatePermission(requestCode: Int) {
        ActivityCompat.requestPermissions(activity!!,
            arrayOf(Manifest.permission.READ_PHONE_STATE),
            requestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_PHONE_STATE) {
            if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                permissionDenied()
            }
        } else if (requestCode == PERMISSION_REQUEST_PHONE_STATE_AFTER_PHONE_ENTERED) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchPinFragment()
            } else {
                permissionDenied()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    private fun permissionDenied() {
        Util.showToast(Constants.PERMISSION_DENIED)
    }

    override fun getTitle() = Constants.SPACE_STRING
    override fun backButtonVisibility() = View.GONE
}
