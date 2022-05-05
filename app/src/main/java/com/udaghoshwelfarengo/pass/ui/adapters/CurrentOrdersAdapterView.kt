package com.udaghoshwelfarengo.pass.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udaghoshwelfarengo.pass.R
import com.udaghoshwelfarengo.pass.models.Asset
import com.udaghoshwelfarengo.pass.models.Order


class CurrentOrdersAdapterView(var currentOrderList : ArrayList<Order>): RecyclerView.Adapter<CurrentOrdersAdapterView.ViewHolder>()  {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderIdText : TextView = itemView.findViewById(R.id.orderIdText)
        val locationText : TextView = itemView.findViewById(R.id.orderLocationText)
        val orderStatusText : TextView = itemView.findViewById(R.id.orderStatusText)
        val assetNameText : TextView = itemView.findViewById(R.id.assetNameText)
        val assetCapacityText : TextView = itemView.findViewById(R.id.assetCapacityText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.order_item_view_layout,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.orderIdText.text = currentOrderList[position].orderId
        holder.locationText.text = currentOrderList[position].deliveryLocation
        holder.orderStatusText.text = currentOrderList[position].status
        holder.assetNameText.text = currentOrderList[position].assetName
        holder.assetCapacityText.text = currentOrderList[position].capacity

    }

    override fun getItemCount(): Int {
        return currentOrderList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateList(currentOrderList: ArrayList<Order>){
        this.currentOrderList = currentOrderList
        notifyDataSetChanged()
    }


}
