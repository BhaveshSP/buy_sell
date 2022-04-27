package com.udaghoshwelfarengo.pass.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.udaghoshwelfarengo.pass.PHONE_NUMBER_KEY
import com.udaghoshwelfarengo.pass.R
import com.udaghoshwelfarengo.pass.SHARED_PREF
import com.udaghoshwelfarengo.pass.network.FirebaseDao
import java.util.concurrent.TimeUnit

class VerifyUserActivity : AppCompatActivity() {

    private var toggle = false
    private var verificationCode : String = ""
    private var phoneNumber : String = ""
    private lateinit var phoneNumberLayout : LinearLayout
    private lateinit var otpLayout : LinearLayout
    private lateinit var verifyButton : Button
    private var enteredVerificationCode : ArrayList<String> = arrayListOf("0","0","0","0","0","0")
    private lateinit var verificationCode1 : EditText
    private lateinit var verificationCode2 : EditText
    private lateinit var verificationCode3 : EditText
    private lateinit var verificationCode4 : EditText
    private lateinit var verificationCode5 : EditText
    private lateinit var verificationCode6 : EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_user)
        phoneNumberLayout  = findViewById(R.id.phoneNumberLayout)
        val phoneNumberText : EditText = findViewById(R.id.phoneNumberText)
        verifyButton = findViewById(R.id.verifyCodeButton)
        otpLayout  = findViewById(R.id.otpCodeLayout)

        verificationCode1 = findViewById(R.id.verificationCode1)
        verificationCode2 = findViewById(R.id.verificationCode2)
        verificationCode3 = findViewById(R.id.verificationCode3)
        verificationCode4 = findViewById(R.id.verificationCode4)
        verificationCode5 = findViewById(R.id.verificationCode5)
        verificationCode6 = findViewById(R.id.verificationCode6)

        verifyButton.setOnClickListener {
            // TODO OTP VERIFY
            if(toggle){
                // Check Sent Code
                checkVerificationCode()
            }else{
                // Send Otp Code
                if(phoneNumberText.text.isEmpty() && phoneNumberText.text.length < 10){
                    Toast.makeText(this, "Enter Valid Phone Number", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                phoneNumber = "+91" + phoneNumberText.text.toString()
                sentVerificationCode(phoneNumber = phoneNumber)
            }
        }
        verificationCode1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!=null){
                    enteredVerificationCode[0] = s.toString()
                }
                if (count==1){
                    verificationCode2.requestFocus(View.FOCUS_DOWN)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        verificationCode2.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!=null){
                    enteredVerificationCode[1] = s.toString()
                }
                if (count==0){
                    verificationCode1.requestFocus(View.FOCUS_DOWN)
                }else if (count == 1){
                    verificationCode3.requestFocus(View.FOCUS_DOWN)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        verificationCode3.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!=null){
                    enteredVerificationCode[2] = s.toString()
                }
                if (count==0){
                    verificationCode2.requestFocus(View.FOCUS_DOWN)
                }else if (count == 1){
                    verificationCode4.requestFocus(View.FOCUS_DOWN)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        verificationCode4.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!=null){
                    enteredVerificationCode[3] = s.toString()
                }
                if (count==0){
                    verificationCode3.requestFocus(View.FOCUS_DOWN)
                }else if (count == 1){
                    verificationCode5.requestFocus(View.FOCUS_DOWN)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        verificationCode5.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!=null){
                    enteredVerificationCode[4] = s.toString()
                }
                if (count==0){
                    verificationCode4.requestFocus(View.FOCUS_DOWN)
                }else if (count == 1){
                    verificationCode6.requestFocus(View.FOCUS_DOWN)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        verificationCode6.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!=null){
                    enteredVerificationCode[5] = s.toString()
                }
                if (count==0){
                    verificationCode5.requestFocus(View.FOCUS_DOWN)
                }else if (count == 1){
                    verifyButton.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }


    private fun sentVerificationCode(phoneNumber: String){
        val options = PhoneAuthOptions.newBuilder()
            .setActivity(this)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    Log.d(TAG, "onVerificationFailed: Done ${p0.smsCode}")
                }
                override fun onVerificationFailed(p0: FirebaseException) {
                    Log.d(TAG, "onVerificationFailed: Error ${p0.message}")
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    verificationCode = p0
                    toggle = true
                    phoneNumberLayout.visibility = View.GONE
                    otpLayout.visibility = View.VISIBLE
                    verifyButton.setText(R.string.verify)
                    Toast.makeText(this@VerifyUserActivity, "Code Sent!", Toast.LENGTH_SHORT).show()
                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun checkVerificationCode(){
        val userCode = enteredVerificationCode[0] + enteredVerificationCode[1] +
                enteredVerificationCode[2] + enteredVerificationCode[3] +
                enteredVerificationCode[4] + enteredVerificationCode[5]

//         Check Via Firebase
        val phoneAuthCredential = PhoneAuthProvider.getCredential(verificationCode,userCode)
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    otpLayout.visibility = View.GONE
                    FirebaseAuth.getInstance().currentUser?.let {
                        FirebaseDao.createUser(phoneNumber,it.uid)
                    }
                    val editor = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit()
                    editor.putString(PHONE_NUMBER_KEY,phoneNumber)
                    editor.apply()
                    Thread{
                        Thread.sleep(2000)
                        val profileIntent = Intent(this,SignInSignUpActivity::class.java)
                        profileIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(profileIntent)
                        finish()
                    }.start()
                }else{
                    Toast.makeText(this, "Incorrect Code", Toast.LENGTH_SHORT).show()
                    verificationCode1.setText("")
                    verificationCode2.setText("")
                    verificationCode3.setText("")
                    verificationCode4.setText("")
                    verificationCode5.setText("")
                    verificationCode6.setText("")
                    verificationCode1.requestFocus()
                }
            }
    }


    companion object{
        private const val TAG = "TESTING"
    }


}