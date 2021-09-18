package com.tkuLife.dorm

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class OkHttpUtil {
    private var mOkHttpClient: OkHttpClient? = null

    companion object {
        val mOkHttpUtil: OkHttpUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            OkHttpUtil()
        }
    }

    init {
        mOkHttpClient=OkHttpClient().newBuilder().build()
    }

    //Get 非同步
    fun getAsync(url: String, callback: ICallback) {
        //Part 2: 宣告 Request，要求要連到指定網址
        val request = with(Request.Builder()) {
            url(url)
            get()
            build()
        }

        //Part 3: 宣告 Call
        val call = mOkHttpClient?.newCall(request)

        //執行 Call 連線後，採用 enqueue 非同步方式，獲取到回應的結果資料
        call?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                callback.onResponse(response)
            }
        })
    }

//    POST方法
    fun post(url: String,json: JSONObject,callback: ICallback){
        request(url,"POST",json,callback)
    }

//    PUT方法
    fun put(url: String,json: JSONObject,callback: ICallback){
        request(url,"PUT",json,callback)
    }


//    request
    private fun request(url: String,method:String ,json: JSONObject, callback: ICallback){
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body= json.toString().toRequestBody(mediaType)
        val request= with(Request.Builder()){
            url(url)
            method(method,body)
            build()
        }

        val call=mOkHttpClient?.newCall(request)

        call?.enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onResponse(response)
            }

        })
    }


    interface ICallback {
        fun onResponse(response: Response)

        fun onFailure(e: IOException)
    }
}