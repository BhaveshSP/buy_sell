package com.udaghoshwelfarengo.pass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.udaghoshwelfarengo.pass.ui.activities.SignInSignUpActivity
import com.udaghoshwelfarengo.pass.ui.activities.VerifyUserActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goToNextActivity()
    }
    private fun goToNextActivity(){
        Thread{
            Thread.sleep(4000)
            startActivity(Intent(this,VerifyUserActivity::class.java))
        }.start()
    }
}