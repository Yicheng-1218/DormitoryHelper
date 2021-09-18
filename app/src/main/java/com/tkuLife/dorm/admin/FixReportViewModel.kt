package com.tkuLife.dorm.admin


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tkuLife.dorm.OkHttpUtil
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class FixReportViewModel: ViewModel() {

    private val repList:MutableLiveData<JSONArray> by lazy {
        MutableLiveData<JSONArray>().also {
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
    }
    private val conList:MutableLiveData<JSONArray> by lazy {
        MutableLiveData<JSONArray>().also {
            OkHttpUtil.mOkHttpUtil.getAsync("https://tkudorm.site/repairList/con",object : OkHttpUtil.ICallback {
                override fun onResponse(response: Response) {
//                回傳報修清單陣列
                    val res = response.body?.string()

                    conList.postValue(JSONArray(res))
                }

                override fun onFailure(e: IOException) {
                }
            })
        }
    }

    fun getRepList():LiveData<JSONArray>{
        return repList
    }
    fun getConList():LiveData<JSONArray>{
        return conList
    }

}