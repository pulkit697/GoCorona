package dev.pulkit.gocorona.api.ctscan

import dev.pulkit.gocorona.models.ctscan.CTScanPost
import dev.pulkit.gocorona.models.ctscan.ResponseMessage
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface CtScanDao {

    @GET("/feed/allctscans")
    suspend fun getAllCTScans():Response<ArrayList<CTScanPost>>

    @GET("/feed/getmyctscans/:{id}")
    suspend fun getMyCTScans(@Path("id") userId:String):Response<ArrayList<CTScanPost>>

    @Multipart
    @POST("/feed/postmyctscan")
    suspend fun postMyCTScan(@Part("userId") userId: RequestBody,
                            @Part("MlReport") mlReport:RequestBody,
                            @Part("timeStamp") timeStamp:RequestBody,
                            @Part file: MultipartBody.Part):Response<ResponseMessage>

    @Multipart
    @POST("/feed/postmycomment")
    suspend fun postMyComment(@Part("id") userId: RequestBody,
                            @Part("time") timeStamp:RequestBody,
                            @Part("doctorName") name:RequestBody,
                            @Part("comment") caption:RequestBody,
                            @Part("result") result:RequestBody):Response<ResponseMessage>
}