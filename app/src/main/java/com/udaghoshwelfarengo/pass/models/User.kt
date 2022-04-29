package com.udaghoshwelfarengo.pass.models

data class User(
    var username : String? = "" ,
    var password : String? = "" ,
    var phoneNumber : String? = "" ,
    var email : String? = "" ,
    var address : String? = "" ,
    var companyName : String? = "" ,
    var panNumber : String? = ""
)