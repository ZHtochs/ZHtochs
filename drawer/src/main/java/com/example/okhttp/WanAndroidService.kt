package com.example.okhttp

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface WanAndroidService {

    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username") userName: String, @Field("password") password: String): Call<ResponseBody>
}