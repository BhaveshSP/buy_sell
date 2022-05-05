package com.udaghoshwelfarengo.pass.models


data class Order(
    var orderId : String? = "" ,
    var assetName : String? = "",
    var deliveryLocation : String? = "",
    var receiverName : String? = "",
    var status : String ?= "Processing",
    var capacity : String? = ""
)