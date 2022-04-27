package com.udaghoshwelfarengo.pass.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.udaghoshwelfarengo.pass.PHONE_NUMBER_KEY
import com.udaghoshwelfarengo.pass.R
import com.udaghoshwelfarengo.pass.SHARED_PREF
import com.udaghoshwelfarengo.pass.models.User
import com.udaghoshwelfarengo.pass.network.FirebaseDao

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val createAccountButton : Button = view.findViewById(R.id.createAccountButton)
        val usernameText : EditText = view.findViewById(R.id.userNameText)
        val passwordText : EditText = view.findViewById(R.id.passwordText)
        val phoneNumberText : EditText = view.findViewById(R.id.phoneNumberText)
        val emailText : EditText = view.findViewById(R.id.emailText)
        val address : EditText = view.findViewById(R.id.addressText)
        val companyName : EditText = view.findViewById(R.id.companyNameText)
        val panNumber : EditText = view.findViewById(R.id.panText)
        createAccountButton.setOnClickListener {
            if(usernameText.text.toString().isEmpty()){
                Toast.makeText(requireContext() , "Enter Valid Username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(passwordText.text.toString().isEmpty()){
                Toast.makeText(requireContext() , "Enter Valid Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(phoneNumberText.text.toString().isEmpty()){
                Toast.makeText(requireContext() , "Enter Valid Phone Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(emailText.text.toString().isEmpty()){
                Toast.makeText(requireContext() , "Enter Valid Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(address.text.toString().isEmpty()){
                Toast.makeText(requireContext() , "Enter Valid Address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(companyName.text.toString().isEmpty()){
                Toast.makeText(requireContext() , "Enter Valid Company Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(panNumber.text.toString().isEmpty()){
                Toast.makeText(requireContext() , "Enter Valid Pan Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val user = User(
                username = usernameText.text.toString(),
                password = passwordText.text.toString(),
                phoneNumber = phoneNumberText.text.toString(),
                email = emailText.text.toString(),
                address = address.text.toString(),
                companyName = companyName.text.toString(),
                panNumber = panNumber.text.toString()
            )
            val phoneNumber = requireActivity().getSharedPreferences(SHARED_PREF,MODE_PRIVATE).getString(
                PHONE_NUMBER_KEY,"")
            if (phoneNumber != null) {
                FirebaseDao.setUserData(user=user,phoneNumber=phoneNumber,authId = FirebaseAuth.getInstance().uid.toString()).addOnCompleteListener{
                    if (it.isComplete){
                        val navHost =  requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
                        navHost.navController.navigate(R.id.action_signUpFragment_to_signInFragment)
                    }else{
                        Toast.makeText(
                            requireContext(),
                            "Error While Setting User Data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }


        }

    }
}