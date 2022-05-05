package com.udaghoshwelfarengo.pass.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.Intent
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
import com.udaghoshwelfarengo.pass.*
import com.udaghoshwelfarengo.pass.models.User
import com.udaghoshwelfarengo.pass.network.FirebaseDao
import com.udaghoshwelfarengo.pass.ui.activities.HomeActivity

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
            val username = usernameText.text.toString()
            val password = passwordText.text.toString()


            FirebaseDao.userDatabaseReference(phoneNumber!!).addOnCompleteListener {
                if (it.isSuccessful){
                    val result = it.result
                    try{
                        val user = result.getValue(User::class.java) as User
                        val editor = requireContext().getSharedPreferences(
                            SHARED_PREF,
                            MODE_PRIVATE).edit()
                        editor.putString(USER_NAME_KEY,user.username)
                        editor.apply()
                        Log.d(TAG, "onViewCreated: Sucucess $user")
                        if(username == user.username && password == user.password){
                            goToHomeActivity()
                        }else{
                            Toast.makeText(
                                requireContext(),
                                "Invalid Username or Password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }catch (e : Exception){
                        Toast.makeText(
                            requireContext(),
                            "User Data not found please Create an Acoount ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }



                }else{
                    Log.d(TAG, "onViewCreated: Error Snap")
                }
            }
        }
        createAccountButton.setOnClickListener{
            val navHost =  requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
            navHost.navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun goToHomeActivity(){
        val editor = requireContext().getSharedPreferences(SHARED_PREF, MODE_PRIVATE).edit()
        editor.putString(USER_STATUS, USER_REGISTERED)
        editor.apply()
        requireContext().startActivity(Intent(requireContext(),HomeActivity::class.java))
        requireActivity().finish()
    }
    companion object{
        private const val TAG = "TESTING"
    }
}