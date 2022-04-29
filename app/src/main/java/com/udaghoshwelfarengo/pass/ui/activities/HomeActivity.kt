package com.udaghoshwelfarengo.pass.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.navigateUp
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.udaghoshwelfarengo.pass.R


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.itemIconTintList = null
        val navController : NavController = Navigation.findNavController(this,R.id.nav_host_fragment_content_main)
        NavigationUI.setupWithNavController(bottomNavigationView,navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
        return (navController.navigateUp() || super.onSupportNavigateUp())
    }

}