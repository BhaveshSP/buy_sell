package com.udaghoshwelfarengo.pass.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
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
import com.udaghoshwelfarengo.pass.models.Asset
import com.udaghoshwelfarengo.pass.models.User
import com.udaghoshwelfarengo.pass.network.FirebaseDao
import com.udaghoshwelfarengo.pass.ui.adapters.AssetAdapterView

class AssetFragment : Fragment() {

    private var assetList : ArrayList<Asset> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asset, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleBar : TextView = view.findViewById(R.id.homeAppbarTitleText)
        titleBar.setText(R.string.asset)
        val addAssetButton : Button = view.findViewById(R.id.addAssetButton)
        val recyclerView : RecyclerView = view.findViewById(R.id.assetsRecyclerView)
        addAssetButton.setOnClickListener {
            val navHost =  requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
            navHost.navController.navigate(R.id.action_assetFragment_to_addAssetFragment)
        }
        val adapter = AssetAdapterView(assetList)
        recyclerView.adapter = adapter

        val phoneNumber = requireActivity().getSharedPreferences(
            SHARED_PREF,
            Context.MODE_PRIVATE
        ).getString(
            PHONE_NUMBER_KEY,"")

        FirebaseDao.getAssetsFromDatabase(phoneNumber!!).addOnCompleteListener{
            assetList =  arrayListOf()
            if (it.isComplete){
                for(child in it.result.children){
                    val asset = child.getValue(Asset::class.java) as Asset
                    assetList.add(asset)
                }
                adapter.updateList(assetList)
                Log.d(TAG, "onViewCreated: Assets Recieved $assetList")
            }else{
                Log.d(TAG, "onViewCreated: Error While Getting Assets")
            }
        }

    }


    companion object{
        private const val TAG = "TESTING"
    }

}