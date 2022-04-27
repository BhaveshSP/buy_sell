package com.udaghoshwelfarengo.pass.network

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.udaghoshwelfarengo.pass.USER_DATABASE
import com.udaghoshwelfarengo.pass.models.User

class FirebaseDao {
    companion object {
        private const val TAG = "TESTINGDAO"

        private val reference = FirebaseDatabase.getInstance().getReference(USER_DATABASE)

        fun createUser(phoneNumber: String, authId: String) {
            reference.child(phoneNumber).child(authId).setValue(true)
        }

        fun setUserData(user: User, phoneNumber: String, authId: String): Task<Void?> {
            return reference.child(phoneNumber).child(authId).setValue(user)
        }

        fun userDatabaseReference(phoneNumber: String,authId: String) : DatabaseReference{
            return reference.child(phoneNumber).child(authId)
        }

    }
}