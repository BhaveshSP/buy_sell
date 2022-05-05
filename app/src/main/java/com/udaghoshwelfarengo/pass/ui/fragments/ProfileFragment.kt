package com.udaghoshwelfarengo.pass.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.udaghoshwelfarengo.pass.PHONE_NUMBER_KEY
import com.udaghoshwelfarengo.pass.R
import com.udaghoshwelfarengo.pass.SHARED_PREF
import com.udaghoshwelfarengo.pass.USER_NAME_KEY
import com.udaghoshwelfarengo.pass.models.User
import com.udaghoshwelfarengo.pass.network.FirebaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleBar : TextView = view.findViewById(R.id.homeAppbarTitleText)
        titleBar.setText(R.string.profile)
        val updateProfileButton : Button = view.findViewById(R.id.updateProfileButton)
        val usernameText : EditText = view.findViewById(R.id.userNameText)
        val passwordText : EditText = view.findViewById(R.id.passwordText)
        val phoneNumberText : EditText = view.findViewById(R.id.phoneNumberText)
        val emailText : EditText = view.findViewById(R.id.emailText)
        val address : EditText = view.findViewById(R.id.addressText)
        val companyName : EditText = view.findViewById(R.id.companyNameText)
        val panNumber : EditText = view.findViewById(R.id.panText)


        val phoneNumber = requireActivity().getSharedPreferences(
            SHARED_PREF,
            Context.MODE_PRIVATE
        ).getString(
            PHONE_NUMBER_KEY,"")

        FirebaseDao.userDatabaseReference(phoneNumber!!).addOnCompleteListener {
            if (it.isSuccessful){
                val result = it.result
                val user = result.getValue(User::class.java) as User
                usernameText.setText(user.username)
                passwordText.setText(user.password)
                phoneNumberText.setText(user.phoneNumber)
                emailText.setText(user.email)
                address.setText(user.address)
                companyName.setText(user.companyName)
                panNumber.setText(user.panNumber)
                val editor = requireContext().getSharedPreferences(
                    SHARED_PREF,
                    Context.MODE_PRIVATE
                ).edit()
                editor.putString(USER_NAME_KEY,user.username)
                editor.apply()
            }else{
                Log.d(TAG, "onViewCreated: Error While Getting Data")
            }
        }

        updateProfileButton.setOnClickListener {
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
            FirebaseDao.setUserData(user=user,phoneNumber=phoneNumber).addOnCompleteListener{
                if (it.isComplete){
                    Toast.makeText(requireContext(), "Data Updated", Toast.LENGTH_SHORT).show()
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
    companion object{

        private const val TAG = "TESTING"

    }

}