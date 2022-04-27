package com.udaghoshwelfarengo.pass.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.udaghoshwelfarengo.pass.PHONE_NUMBER_KEY
import com.udaghoshwelfarengo.pass.R
import com.udaghoshwelfarengo.pass.SHARED_PREF
import com.udaghoshwelfarengo.pass.network.FirebaseDao

class SignInFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val createAccountButton : Button = view.findViewById(R.id.createAccountButton)
        val signInButton : Button = view.findViewById(R.id.signInButton)
        val usernameText : EditText = view.findViewById(R.id.userNameText)
        val passwordText : EditText = view.findViewById(R.id.passwordText)
        signInButton.setOnClickListener {
            // TODO VALIDATE DATA
            if (usernameText.text.isEmpty()){
                Toast.makeText(requireContext(), "Enter Valid Username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passwordText.text.isEmpty()){
                Toast.makeText(requireContext(), "Enter Valid Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val phoneNumber = requireActivity().getSharedPreferences(SHARED_PREF,MODE_PRIVATE).getString(
                PHONE_NUMBER_KEY,"")
            val userCheckListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        Log.d(TAG, "onDataChange: $snapshot")
                        Log.d(TAG, "onDataChange: ${snapshot.children}")
                        for (child in snapshot.children){
                            Log.d(TAG, "onDataChange: $child")
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "onCancelled: Error While Checking If User Exists")
                }
            }
            FirebaseDao.userDatabaseReference(phoneNumber!!,FirebaseAuth.getInstance().uid.toString())
                .addListenerForSingleValueEvent(userCheckListener)
        }
        createAccountButton.setOnClickListener{
            val navHost =  requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
            navHost.navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }
    companion object{
        private const val TAG = "TESTING"
    }
}