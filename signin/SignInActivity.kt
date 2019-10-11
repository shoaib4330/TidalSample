package com.careem.mt.adma.ui.signin

import android.app.Activity
import android.os.Bundle
import com.careem.mt.adma.R
import com.careem.mt.adma.utils.NavigationUtils
import kotlinx.android.synthetic.main.activity_signin.*

class SignInActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        tvSignin.setOnClickListener { launchMobileNumberFragment() }
    }

    private fun launchMobileNumberFragment() {
        NavigationUtils.launchBaseFragmentActivity(this,PhoneNumberFragment::class.java.name)
        finish()
    }
}
