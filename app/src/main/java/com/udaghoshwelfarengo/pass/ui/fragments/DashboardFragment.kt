package com.udaghoshwelfarengo.pass.ui.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.udaghoshwelfarengo.pass.PHONE_NUMBER_KEY
import com.udaghoshwelfarengo.pass.R
import com.udaghoshwelfarengo.pass.SHARED_PREF
import com.udaghoshwelfarengo.pass.USER_NAME_KEY
import com.udaghoshwelfarengo.pass.models.Order
import com.udaghoshwelfarengo.pass.models.User
import com.udaghoshwelfarengo.pass.network.FirebaseDao
import com.udaghoshwelfarengo.pass.ui.adapters.CurrentOrdersAdapterView

class DashboardFragment : Fragment() {
    private var orderList : ArrayList<Order> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleBar : TextView= view.findViewById(R.id.homeAppbarTitleText)
        titleBar.setText(R.string.dash_board)
        val ongoingOrderRecyclerView : RecyclerView = view.findViewById(R.id.allOrdersRecyclerView)
        val username = requireContext().getSharedPreferences(SHARED_PREF,MODE_PRIVATE).getString(
            USER_NAME_KEY,"Test")
        val userNameGreetingText : TextView = view.findViewById(R.id.userNameGreetingText)
        userNameGreetingText.text = getString(R.string.user_greeting_text,username)
        val adapter = CurrentOrdersAdapterView(orderList)
        ongoingOrderRecyclerView.adapter = adapter

        val phoneNumber = requireActivity().getSharedPreferences(
            SHARED_PREF,
            Context.MODE_PRIVATE
        ).getString(
            PHONE_NUMBER_KEY,"")

        FirebaseDao.getOrdersFromDatabase(phoneNumber!!).addOnCompleteListener {
            orderList  = arrayListOf()
            if (it.isComplete){
                for(child in it.result.children){
                    val order = child.getValue(Order::class.java) as Order
                    orderList.add(order)
                }
                adapter.updateList(orderList)
            }else{
                Log.d(TAG, "onViewCreated: Error While Getting Orders")
            }
        }

    }
    companion object{
        private const val TAG = "TESTING"
    }
}