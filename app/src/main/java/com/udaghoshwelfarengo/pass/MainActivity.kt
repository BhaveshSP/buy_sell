package com.udaghoshwelfarengo.pass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.udaghoshwelfarengo.pass.ui.activities.HomeActivity
import com.udaghoshwelfarengo.pass.ui.activities.SignInSignUpActivity
import com.udaghoshwelfarengo.pass.ui.activities.VerifyUserActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goToNextActivity()
    }
    private fun goToNextActivity(){
//        FirebaseAuth.getInstance().signOut()
        val user = FirebaseAuth.getInstance().currentUser
        val intent: Intent
        if (user == null){
            intent = Intent(this,VerifyUserActivity::class.java)
        }else{
            val userState = getSharedPreferences(SHARED_PREF, MODE_PRIVATE).getString(USER_STATUS,
                USER_VERIFIED)
            if (userState == USER_VERIFIED){
                intent = Intent(this,SignInSignUpActivity::class.java)
            }else{
                intent = Intent(this,HomeActivity::class.java)
            }
        }
        Thread{
            Thread.sleep(4000)
            startActivity(intent)
            finish()
        }.start()
    }
}