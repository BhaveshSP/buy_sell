package com.udaghoshwelfarengo.pass.network

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.udaghoshwelfarengo.pass.ASSET_DATABASE
import com.udaghoshwelfarengo.pass.DETAILS
import com.udaghoshwelfarengo.pass.ORDERS_DATABASE
import com.udaghoshwelfarengo.pass.USER_DATABASE
import com.udaghoshwelfarengo.pass.models.Asset
import com.udaghoshwelfarengo.pass.models.Order
import com.udaghoshwelfarengo.pass.models.User

class FirebaseDao {
    companion object {
        private const val TAG = "TESTINGDAO"

        private val reference = FirebaseDatabase.getInstance().getReference(USER_DATABASE)


        fun createUser(phoneNumber: String) {
            reference.child(phoneNumber).child(DETAILS).setValue(true)
        }

        fun setUserData(user: User, phoneNumber: String): Task<Void?> {
            return reference.child(phoneNumber).child(DETAILS).setValue(user)
        }

        fun userDatabaseReference(phoneNumber: String) : Task<DataSnapshot>{
            return reference.child(phoneNumber).child(DETAILS).get()
        }

        fun addAssetToDatabase(phoneNumber: String,asset : Asset) : Task<Void?>{
            val assetReference = reference.child(phoneNumber).child(ASSET_DATABASE)
            return assetReference.push().setValue(asset)
        }

        fun getAssetsFromDatabase(phoneNumber: String) : Task<DataSnapshot>{
            val assetReference = reference.child(phoneNumber).child(ASSET_DATABASE)
            return assetReference.get()
        }


        fun addOrdersToDatabase(phoneNumber: String,order : Order) : Task<Void?>{
            val orderReference = reference.child(phoneNumber).child(ORDERS_DATABASE)
            return orderReference.push().setValue(order)
        }

        fun getOrdersFromDatabase(phoneNumber: String) : Task<DataSnapshot>{
            val orderReference = reference.child(phoneNumber).child(ORDERS_DATABASE)
            return orderReference.get()
        }

    }

}