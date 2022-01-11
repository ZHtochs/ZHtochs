package com.example.drawer

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import okhttp3.*
import org.junit.Test
import java.io.IOException


class OkHttpTest {
    public val URL = "https://www.httpbin.org/"

    @Test
    @Throws(IOException::class)
    fun uploadFile() {
//        val okHttpClient = OkHttpClient()
//        val file =
//            File("C:\\Users\\zhuhe\\AndroidStudioProjects\\MyDemo\\drawer\\src\\main\\res\\layout\\activity_main.xml")
//        val file2 =
//            File("C:\\Users\\zhuhe\\AndroidStudioProjects\\MyDemo\\drawer\\src\\main\\res\\layout\\activity_main_test.xml")
//        val build: MultipartBody = MultipartBody.Builder()
//            .addFormDataPart("file1", file.name, RequestBody.create("text/plain".toMediaTypeOrNull(), file))
//            .addFormDataPart("file2", file2.name, RequestBody.create("text/plain".toMediaTypeOrNull(), file2))
//            .build()
//        val request: Request = Request.Builder().url(URL + "post").post(build).build()
//        val execute = okHttpClient.newCall(request).execute()
//        println(execute.code)
//        println(execute.body?.string())


        val url = "http://wwww.baidu.com"
        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url(url)
            .build()
        val call = okHttpClient.newCall(request)
        val execute = call.execute();
        println(execute.body()?.string())

    }

    @Test
    @Throws(IOException::class)
    fun uploadJson() {
        val okHttpClient = OkHttpClient()
        val jsonObject = JsonObject()
        jsonObject.add("1", JsonPrimitive(1))
        jsonObject.add("2", JsonPrimitive(2))
        jsonObject.add("3", JsonPrimitive(3))
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())
        val request: Request = Request.Builder().url(URL + "post").post(requestBody).build()
        val execute = okHttpClient.newCall(request).execute()
        println(execute.code())
        println(execute.body()?.string())
    }

    //*
    // 拦截器
    // */
    @Test
    fun addIntercept() {
        val okHttpClient: OkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor { it ->
                val request = it.request()
                val addHeader =
                    request.newBuilder().addHeader("os", "android")
                        .addHeader("version", BuildConfig.VERSION_NAME).build()
                it.proceed(addHeader)
            }
            .build()
        val request = Request.Builder().url(URL + "get?a=1&b=2").build()
        try {
            val execute = okHttpClient.newCall(request).execute()
            println(execute.body()?.string())
            println(execute.code())
        } catch (e: Exception) {
            println("Exception ${e.javaClass}")
        }
    }


    val URL2 = "https://www.wanandroid.com/"

    var cacheCookies = listOf<Cookie>()


    @Test
    fun cookieTest() {
        val okHttpClient = OkHttpClient.Builder().cookieJar(object : CookieJar {
            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                return if (url.host().contains("wanandroid.com")) {
                    cacheCookies
                } else {
                    listOf()
                }
            }

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cacheCookies = cookies
            }
        }).build()
        val formBody = FormBody.Builder().add("username", "Oreki_Eru")
            .add("password", "123456").build()
        var request: Request = Request.Builder().url(URL2 + "user/login").post(formBody).build()
        val execute = okHttpClient.newCall(request).execute()
        println(execute.code())
        println(execute.body()?.string())

        request = Request.Builder().url(URL2 + "lg/collect/list/0/json").build()
        val execute2 = okHttpClient.newCall(request).execute()
        println(execute2.code())
        println(execute2.body()?.string())
    }
}