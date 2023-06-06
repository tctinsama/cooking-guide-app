package com.example.dacs_3

import com.example.dacs_3.Model.Account
import com.example.dacs_3.Model.Danhmuc
import com.example.dacs_3.Model.Lichmonan
import com.example.dacs_3.Model.Monan
import okhttp3.ResponseBody
import retrofit2.http.*

interface API {
    @POST("register.php")
    suspend fun Register(@Body account:Account)

    @POST("login.php")
    suspend fun Login(@Body account:Account):Account

    @GET("display.php")
    suspend fun getDanhmuc(): ResponseBody

    @POST("add.php")
    suspend fun getInsert(@Body danhmuc: Danhmuc)

    @POST("update.php")
    suspend fun getUpdate(@Body danhmuc: Danhmuc)

    @POST("delete.php")
    suspend fun getDelete(@Body danhmuc: Danhmuc)

    @FormUrlEncoded
    @POST("display-monan.php")
    suspend fun getMonan(@Field("iddm") iddm:Int?): ResponseBody

    @FormUrlEncoded
    @POST("display-monanyt.php")
    suspend fun getMonanyt(@Field("trangthai") trangthai:Int?): ResponseBody


    @FormUrlEncoded
    @POST("add-monan.php")
    suspend fun getInsertMA(@Field("tenma") tenma:String?,
                            @Field("motama") motama:String?,
                            @Field("nguyenlieu") nguyenlieu:String?,
                            @Field("tendm") tendm:String?)

    @POST("update-monan.php")
    suspend fun getUpdateMA(@Body monan: Monan)

    @POST("delete-monan.php")
    suspend fun getDeleteMA(@Body monan: Monan)

    @FormUrlEncoded
    @POST("updatetrangthai.php")
    suspend fun update_tt(@Field("idma") idma:Int)

    @FormUrlEncoded
    @POST("display_lichma.php")
    suspend fun getlichma(@Field("ngay") ngay:String): ResponseBody

    @GET("display_monday.php")
    suspend fun getChonmon(): ResponseBody

    @POST("insert_monday.php")
    suspend fun InsertMonday(@Body lichmonan: Lichmonan)

    @FormUrlEncoded
    @POST("delete-lichma.php")
    suspend fun getDeleteMonday(@Field("id") id:Int?)

    @FormUrlEncoded
    @POST("search.php")
    suspend fun search(@Field("text") text:String?,
                       @Field("iddm") iddm:Int?):ResponseBody
}