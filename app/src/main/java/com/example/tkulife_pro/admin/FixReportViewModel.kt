package com.example.tkulife_pro.admin


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tkulife_pro.OkHttpUtil
import kotlinx.coroutines.runBlocking
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class FixReportViewModel: ViewModel() {

    private val repList:MutableLiveData<JSONArray> by lazy {
        MutableLiveData<JSONArray>().also {
            repRequest()

        }
    }
    private val conList:MutableLiveData<JSONArray> by lazy {
        MutableLiveData<JSONArray>().also {
//TODO
        }
    }

    fun getRepList():LiveData<JSONArray>{
        return repList
    }
    fun getConList():LiveData<JSONArray>{
        return conList
    }

    private fun repRequest(){
        //        request報修清單(rep)
        OkHttpUtil.mOkHttpUtil.getAsync("https://tkudorm.site/repairList/rep",object : OkHttpUtil.ICallback {
            override fun onResponse(response: Response) {
//                回傳報修清單陣列
                val res = response.body?.string()

                repList.postValue(JSONArray(res))

            }

            override fun onFailure(e: IOException) {

            }
        })
    }
    private fun conRequest(){
//TODO
    }
}