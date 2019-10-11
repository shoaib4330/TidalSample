package com.careem.mt.adma.ui.signin

import android.content.Context
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.careem.mt.adma.ui.BaseFragment
import com.careem.mt.adma.ui.termsofuse.TermsOfUseFragment
import com.careem.mt.adma.utils.Constants
import com.careem.mt.adma.utils.NavigationUtils

private const val DELAY: Long = 500

abstract class BasePinFragment : BaseFragment() {

    private val start = 0

    private lateinit var etPin1: EditText
    private lateinit var etPin2: EditText
    private lateinit var etPin3: EditText
    private lateinit var etPin4: EditText

    lateinit var pin: String

    fun initEditTexts(etPin1: EditText, etPin2: EditText, etPin3: EditText, etPin4: EditText) {
        this.etPin1 = etPin1
        this.etPin2 = etPin2
        this.etPin3 = etPin3
        this.etPin4 = etPin4
        setKeyListeners()
        setupTextWatchers()
    }

    private fun setKeyListeners() {
        etPin1.requestFocus()
        etPin1.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val mgr = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                // only will trigger it if no physical keyboard is
                // open
                mgr.showSoftInput(etPin1,
                    InputMethodManager.SHOW_IMPLICIT)

                etPin1.transformationMethod = null
            }
        }
        etPin1.setOnKeyListener({ _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_DEL) requestFocus(etPin1, true)
            }
            false
        })
        etPin2.setOnKeyListener({ _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_DEL) requestFocus(etPin1, true)
            }
            false
        })
        etPin3.setOnKeyListener({ _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_DEL) requestFocus(etPin2, true)
            }
            false
        })
        etPin4.setOnKeyListener({ _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) if (keyCode == KeyEvent.KEYCODE_DEL) {
                requestFocus(etPin3, true)
            }
            false
        })
    }

    private fun setupTextWatchers() {
        etPin1.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (etPin1.text.toString().length == 1) requestFocus(etPin2, false)
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })
        etPin2.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (etPin2.text.toString().length == 1) {
                        etPin1.transformationMethod = PasswordTransformationMethod.getInstance()
                        requestFocus(etPin3, false)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })
        etPin3.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (etPin3.text.toString().length == 1) {
                        etPin2.transformationMethod = PasswordTransformationMethod.getInstance()
                        requestFocus(etPin4, false)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })
        etPin4.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (etPin4.text.toString().length == 1) {
                        etPin3.transformationMethod = PasswordTransformationMethod.getInstance()
                        Handler().postDelayed({
                            etPin4.transformationMethod = PasswordTransformationMethod.getInstance()
                        }, DELAY)
                        performAuthentication(false)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })
    }

    private fun requestFocus(editText: EditText, isDelPressed: Boolean) {
        if (isDelPressed) editText.transformationMethod = null

        editText.requestFocus()
        editText.setSelection(start, editText.text.toString().length)
    }

    private fun performAuthentication(isDelPressed: Boolean) {
        if (isPinCodeIsFilled() && !isDelPressed) {
            validateCode()
        }
    }


    private fun isPinCodeIsFilled(): Boolean {
        pin = Constants.EMPTY_STRING
        val pinCodes = arrayOf(etPin1, etPin2, etPin3, etPin4)
        for (pinCode in pinCodes) {
            if (pinCode.text.toString().isEmpty()) {
                return false
            } else {
                pin += pinCode.text.toString()
            }
        }
        return true
    }

    fun navigateToTermsAndUseScreen() {
        NavigationUtils.launchBaseFragmentActivity(activity!!, TermsOfUseFragment::class.java.name)
        activity!!.finish()
    }

    fun navigateToMainActivity() {
        NavigationUtils.launchMainActivity(activity!!)
        activity!!.finish()
    }

    fun resetFields() {
        etPin1.text.clear()
        etPin1.transformationMethod = null

        etPin2.text.clear()
        etPin2.transformationMethod = null

        etPin3.text.clear()
        etPin3.transformationMethod = null

        etPin4.text.clear()
        etPin4.transformationMethod = null

        etPin1.requestFocus()
    }

    abstract fun validateCode()
}
