package com.udaghoshwelfarengo.pass.ui.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
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

class OrderingFragment : Fragment() {

    private var orderList : ArrayList<Order> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ordering, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleBar : TextView = view.findViewById(R.id.homeAppbarTitleText)
        titleBar.setText(R.string.ordering)
        val addAssetButton : Button = view.findViewById(R.id.addOrderButton)
        val recyclerView : RecyclerView = view.findViewById(R.id.ordersRecyclerView)
        val currentOrderAdapter = CurrentOrdersAdapterView(orderList)
        recyclerView.adapter = currentOrderAdapter

        val phoneNumber = requireActivity().getSharedPreferences(
            SHARED_PREF,
            Context.MODE_PRIVATE
        ).getString(
            PHONE_NUMBER_KEY,"")

        FirebaseDao.getOrdersFromDatabase(phoneNumber!!).addOnCompleteListener {
            orderList = arrayListOf()
            if (it.isComplete){
                for(child in it.result.children){
                    val order = child.getValue(Order::class.java) as Order
                    orderList.add(order)
                }
                val editor = requireActivity().getSharedPreferences(SHARED_PREF,MODE_PRIVATE).edit()
                editor.putString(TOTAL_ORDERS,orderList.size.toString())
                editor.apply()
                currentOrderAdapter.updateList(orderList)
                Log.d(TAG, "onViewCreated: Orders Recieved $orderList")
            }else{
                Log.d(TAG, "onViewCreated: Error While Getting Orders")
            }
        }
        addAssetButton.setOnClickListener {
            val navHost =  requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
            navHost.navController.navigate(R.id.action_orderingFragment_to_addOrderFragment)
        }
    }

    companion object{
        private const val TAG = "TESTING"
    }

}