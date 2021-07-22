package com.example.tkulife_pro.admin.fixReport.fixTab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.tkulife_pro.OkHttpUtil
import com.example.tkulife_pro.databinding.ActivityDescribePageBinding
import com.example.tkulife_pro.OkHttpUtil.Companion.mOkHttpUtil
import com.example.tkulife_pro.admin.fixReport.FixNotification
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class DescribePage : AppCompatActivity() {
    private lateinit var binding: ActivityDescribePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescribePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
//        取得報修項目報修內容
        val rep = intent.getStringExtra("rep")

//        取得報修項目index
        val index = intent.getStringExtra("index")

//        設定描述框內文字
        binding.editText.text = rep

//        確定故障監聽
        binding.button15.setOnClickListener {
            val confirm = AlertDialog.Builder(this)
            confirm.setMessage("確認要將狀態改為故障中嗎?")
            confirm.setTitle("確認視窗")
            confirm.setNegativeButton("取消", null)
            confirm.setPositiveButton("確定") { _, _ ->
//                請求附加資料
                val body=JSONObject("{'index':'${index}' , 'con' : 'broken' , 'key' : 'rep'} ")
//                管理員put請求(broken)
                mOkHttpUtil.put(
                    "https://tkudorm.site/repair",
                    body,
                    object : OkHttpUtil.ICallback {
//                        非同步線程
                        override fun onResponse(response: Response) {
                            val res = response.body?.string()
                            runOnUiThread {
//                                UI線程

//                                顯示server回傳
                                Toast.makeText(
                                    this@DescribePage,
                                    JSONObject(res!!)["msg"].toString(),
                                    Toast.LENGTH_LONG
                                ).show()

//                                導向到報修提醒
                                Intent(this@DescribePage, FixNotification::class.java).apply {
                                    putExtra("Page", 1)
                                    startActivity(this)
                                }
//                                結束Activity
                                finish()
                            }
                        }

                        override fun onFailure(e: IOException) {

                        }
                    })
            }.show()
        }

//        無故障按鈕監聽
        binding.button14.setOnClickListener {
            val confirm = AlertDialog.Builder(this)
            confirm.setMessage("確認此機台無故障嗎?")
            confirm.setTitle("確認視窗")
            confirm.setNegativeButton("取消", null)
            confirm.setPositiveButton("確定") { _, _ ->
//                管理員put請求(usable)
                mOkHttpUtil.put(
                    "https://tkudorm.site/repair",
//                    請求附加資料(index,con,key)
                    JSONObject("{'key':'rep', 'index':'${index}' , 'con' : 'usable'} "),
                    object : OkHttpUtil.ICallback {
                        override fun onResponse(response: Response) {
//                            非同步線程
                            val res = response.body?.string()
                            runOnUiThread {
//                                UI線程

//                                顯示sever回傳
                              Toast.makeText(
                                    this@DescribePage,
                                    JSONObject(res!!)["msg"].toString(),
                                    Toast.LENGTH_LONG
                                ).show()

//                                結束Activity
                                finish()
                            }
                        }

                        override fun onFailure(e: IOException) {
                        }
                    })
            }.show()
        }
    }
}
