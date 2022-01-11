package com.example.okhttp

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

object RetrofitTest2 {
    val TAG = "RetrofitTest2"
    val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(OkHttpTest.URL).build()
    }
    val retrofitTest = retrofit.create(RetrofitTest::class.java)

    fun postLogin() {
        val post = retrofitTest.post("123", "asd")
        post.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                println("onResponse")
                println(response.code())
                println(response.body()?.string())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("onFailure")
            }
        })
    }


    fun getLogin() {
        val post = retrofitTest.get("123", "asd")
        post.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                println("onResponse")
                println(response.code())
                println(response.body()?.string())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("onFailure")
            }
        })
    }



}