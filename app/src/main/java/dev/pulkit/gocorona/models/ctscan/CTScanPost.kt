package dev.pulkit.gocorona.models.ctscan

data class CTScanPost(val imageUrl:String,val timeStamp:Int,val userId:Int,val MlReport:Int,val comments:ArrayList<Comment>){}