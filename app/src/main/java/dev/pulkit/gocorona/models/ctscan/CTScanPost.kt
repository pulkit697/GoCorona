package dev.pulkit.gocorona.models.ctscan

data class CTScanPost(val imageUrl:String,val timeStamp:String,val userId:String,val MlReport:String,val comments:ArrayList<Comment>){}