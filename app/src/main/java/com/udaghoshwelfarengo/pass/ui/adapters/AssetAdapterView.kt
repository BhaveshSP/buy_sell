package com.udaghoshwelfarengo.pass.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udaghoshwelfarengo.pass.R
import com.udaghoshwelfarengo.pass.models.Asset

class AssetAdapterView(var assetList : ArrayList<Asset>): RecyclerView.Adapter<AssetAdapterView.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.asset_item_view_layout,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asset = assetList[position]
        holder.assetName.text = asset.name
        holder.assetCapacity.text = asset.capacity
        holder.assetType.text = asset.type
        holder.location.text = asset.location
    }

    override fun getItemCount(): Int {
        return assetList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(assetList: ArrayList<Asset>){
        this.assetList = assetList
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val assetName : TextView = itemView.findViewById(R.id.assetNameText)
        val assetCapacity : TextView = itemView.findViewById(R.id.assetCapacityText)
        val location : TextView = itemView.findViewById(R.id.assetLocationText)
        val assetType : TextView = itemView.findViewById(R.id.assetTypeText)
    }




}