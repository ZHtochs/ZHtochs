package com.example.drawer

import com.example.okhttp.RetrofitTest2
import com.example.okhttp.WanAndroidService
import org.junit.Test
import retrofit2.Retrofit

public class RetrofitUnitTest {

    val URL = "https://www.httpbin.org/"

    val WanAndroidURL = "https://www.wanandroid.com/"

    @Test
    fun postLogin() {
        RetrofitTest2.postLogin()
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
        }
    }

    @Test
    fun getLogin() {
        RetrofitTest2.getLogin()
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
        }
    }

    @Test
    fun wanAndroidLogin() {
        val retrofit = Retrofit.Builder().baseUrl(WanAndroidURL).build()
        val wanAndroidService = retrofit.create(WanAndroidService::class.java)
        val login = wanAndroidService.login("Oreki_Eru", "123456")
        val body = login.execute()
        println(body.code())
        println(body.body()?.string())
    }
}