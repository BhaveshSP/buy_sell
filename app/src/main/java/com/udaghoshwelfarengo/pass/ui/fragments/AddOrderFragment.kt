package com.udaghoshwelfarengo.pass.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.NavHostFragment
import com.udaghoshwelfarengo.pass.PHONE_NUMBER_KEY
import com.udaghoshwelfarengo.pass.R
import com.udaghoshwelfarengo.pass.SHARED_PREF
import com.udaghoshwelfarengo.pass.models.Asset
import com.udaghoshwelfarengo.pass.models.Order
import com.udaghoshwelfarengo.pass.network.FirebaseDao
import java.util.*
import kotlin.collections.ArrayList

class AddOrderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleBar : TextView = view.findViewById(R.id.homeAppbarTitleText)
        titleBar.setText(R.string.add_order)
        val assetNameSpinner : Spinner = view.findViewById(R.id.selectAssetNameSpinner)
        val locationText : EditText = view.findViewById(R.id.locationText)
        val receiverNameText : EditText = view.findViewById(R.id.receiverNameText)
        val assetCapacityText : EditText = view.findViewById(R.id.assetCapacityText)
        var assetNames : ArrayList<String> = arrayListOf()
        val addButton : Button = view.findViewById(R.id.addButton)
        var assetName : String = ""
        assetNameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                assetName = assetNames[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }


        }
        addButton.setOnClickListener {
            if(locationText.text.toString().isEmpty()){
                Toast.makeText(requireContext() , "Enter Valid Location", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(receiverNameText.text.toString().isEmpty()){
                Toast.makeText(requireContext() , "Enter Valid Receiver Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(assetCapacityText.text.toString().isEmpty()){
                Toast.makeText(requireContext() , "Enter Valid Capacity", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val order = Order(
                orderId = UUID.randomUUID().toString(),
                assetName = assetName,
                deliveryLocation = locationText.text.toString(),
                capacity = assetCapacityText.text.toString(),
                status = "Processing",
                receiverName = receiverNameText.text.toString()
            )

            val phoneNumber = requireActivity().getSharedPreferences(
                SHARED_PREF,
                Context.MODE_PRIVATE
            ).getString(
                PHONE_NUMBER_KEY,"")

            FirebaseDao.addOrdersToDatabase(phoneNumber = phoneNumber!!,order).addOnCompleteListener {
                if (it.isComplete){
                    Toast.makeText(requireContext() , "Order Added Successfully ", Toast.LENGTH_SHORT).show()
                    val navHost =  requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
                    navHost.navController.navigate(R.id.action_addOrderFragment_to_orderingFragment)
                }else{
                    Toast.makeText(requireContext() , "Error Asset Not Added", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val phoneNumber = requireActivity().getSharedPreferences(
            SHARED_PREF,
            Context.MODE_PRIVATE
        ).getString(
            PHONE_NUMBER_KEY,"")
        FirebaseDao.getAssetsFromDatabase(phoneNumber!!).addOnCompleteListener{
            assetNames = arrayListOf()
            if (it.isComplete){
                for(child in it.result.children){
                    val asset = child.getValue(Asset::class.java) as Asset
                    assetNames.add(asset.name!!)
                }
                val adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_dropdown_item_1line,assetNames)
                assetNameSpinner.adapter  = adapter
                if (assetName.length > 1){
                    assetName = assetNames[0]
                }
            }
        }
    }

}