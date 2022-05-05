package com.udaghoshwelfarengo.pass.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udaghoshwelfarengo.pass.PHONE_NUMBER_KEY
import com.udaghoshwelfarengo.pass.R
import com.udaghoshwelfarengo.pass.SHARED_PREF
import com.udaghoshwelfarengo.pass.TOTAL_ORDERS
import com.udaghoshwelfarengo.pass.models.Asset
import com.udaghoshwelfarengo.pass.models.Order
import com.udaghoshwelfarengo.pass.network.FirebaseDao
import com.udaghoshwelfarengo.pass.ui.adapters.CurrentOrdersAdapterView
import com.udaghoshwelfarengo.pass.ui.adapters.PastOrdersAdapterView

class HistoryFragment : Fragment() {
    private var pastOrderList : ArrayList<Order> = arrayListOf()
    private var currentOrderList : ArrayList<Order> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleBar : TextView = view.findViewById(R.id.homeAppbarTitleText)
        titleBar.setText(R.string.history)
        val pastOrdersRecyclerView : RecyclerView =  view.findViewById(R.id.pastOrdersRecyclerView)
        val currentOrderRecyclerView : RecyclerView = view.findViewById(R.id.currentOrdersRecyclerView)
        val pastOrderAdapter = PastOrdersAdapterView(pastOrderList)
        val currentOrderAdapter = CurrentOrdersAdapterView(currentOrderList)
        pastOrdersRecyclerView.adapter = pastOrderAdapter
        currentOrderRecyclerView.adapter = currentOrderAdapter

        val phoneNumber = requireActivity().getSharedPreferences(
            SHARED_PREF,
            Context.MODE_PRIVATE
        ).getString(
            PHONE_NUMBER_KEY,"")

        FirebaseDao.getOrdersFromDatabase(phoneNumber!!).addOnCompleteListener {
            pastOrderList = arrayListOf()
            currentOrderList = arrayListOf()
            if (it.isComplete){
                for(child in it.result.children){
                    val order = child.getValue(Order::class.java) as Order
                    if (order.status == "Done"){
                        pastOrderList.add(order)
                    }else{
                        currentOrderList.add(order)
                    }
                }
                val editor = requireActivity().getSharedPreferences(SHARED_PREF,
                    Context.MODE_PRIVATE
                ).edit()
                editor.putString(TOTAL_ORDERS,pastOrderList.size.toString())
                editor.apply()
                pastOrderAdapter.updateList(pastOrderList)
                currentOrderAdapter.updateList(currentOrderList)
            }else{
                Log.d(TAG, "onViewCreated: Error While Getting Orders")

            }
        }
    }

    companion object{
        private const val TAG = "TESTING"
    }

}