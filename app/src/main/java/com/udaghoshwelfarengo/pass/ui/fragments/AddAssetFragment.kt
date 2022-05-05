package com.udaghoshwelfarengo.pass.ui.fragments

import android.content.Context
import android.os.Bundle
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
import com.udaghoshwelfarengo.pass.network.FirebaseDao


class AddAssetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_asset, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleBar : TextView = view.findViewById(R.id.homeAppbarTitleText)
        titleBar.setText(R.string.add_asset)
        val assetTypeSpinner : Spinner = view.findViewById(R.id.selectAssetTypeSpinner)
        val locationText : EditText = view.findViewById(R.id.locationText)
        val assetNameText : EditText = view.findViewById(R.id.assetNameText)
        val assetCapacityText : EditText = view.findViewById(R.id.assetCapacityText)
        val assetTypes : ArrayList<String> = arrayListOf()
        val addButton : Button = view.findViewById(R.id.addButton)

        assetTypes.add("Type 1")
        assetTypes.add("Type 2")
        assetTypes.add("Type 3")
        assetTypes.add("Type 4")
        val adapter : ArrayAdapter<String> = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line,assetTypes)
        assetTypeSpinner.adapter = adapter
        var assetType = assetTypes[0]
        assetTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                assetType = assetTypes[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        addButton.setOnClickListener {
            if(locationText.text.toString().isEmpty()){
                Toast.makeText(requireContext() , "Enter Valid Location", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(assetNameText.text.toString().isEmpty()){
                Toast.makeText(requireContext() , "Enter Valid Asset Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(assetCapacityText.text.toString().isEmpty()){
                Toast.makeText(requireContext() , "Enter Valid Asset Capacity", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val asset = Asset(
                name = assetNameText.text.toString(),
                location = locationText.text.toString(),
                capacity = assetCapacityText.text.toString(),
                type = assetType
            )

            val phoneNumber = requireActivity().getSharedPreferences(
                SHARED_PREF,
                Context.MODE_PRIVATE
            ).getString(
                PHONE_NUMBER_KEY,"")

            FirebaseDao.addAssetToDatabase(phoneNumber = phoneNumber!!,asset).addOnCompleteListener {
                if (it.isComplete){
                    Toast.makeText(requireContext() , "Asset Added Successfully ", Toast.LENGTH_SHORT).show()
                    val navHost =  requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
                    navHost.navController.navigate(R.id.action_addAssetFragment_to_assetFragment)
                }else{
                    Toast.makeText(requireContext() , "Error Asset Not Added", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}