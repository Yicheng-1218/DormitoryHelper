package com.example.tkulife_pro.admin.fixreport.FixTab

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.tkulife_pro.OkHttpUtil
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.ActivityDescribePageBinding
import com.example.tkulife_pro.OkHttpUtil.Companion.mOkHttpUtil
import com.example.tkulife_pro.admin.fixreport.FixNotification
import com.google.android.material.snackbar.Snackbar
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class DescribePage : AppCompatActivity() {
    private lateinit var binding: ActivityDescribePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescribePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()


    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
    private fun initView() {
        val rep = intent.getStringExtra("rep")
        val index = intent.getStringExtra("index")
        binding.editText.text = rep

        binding.button15.setOnClickListener {
            val confirm = AlertDialog.Builder(this)
            confirm.setMessage("確認要將狀態改為故障中嗎?")
            confirm.setTitle("確認視窗")
            confirm.setNegativeButton("取消", null)
            confirm.setPositiveButton("確定",object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    mOkHttpUtil.put("https://tkudorm.site/repair", JSONObject("{'index':'${index}' , 'con' : 'broken' , 'key' : 'rep'} "),object : OkHttpUtil.ICallback{
                        override fun onResponse(response: Response) {
                            val res = response.body?.string()
                            runOnUiThread{
                                Toast.makeText(this@DescribePage,JSONObject(res)["msg"].toString(),Toast.LENGTH_LONG).show()
                                Intent(this@DescribePage,FixNotification::class.java).apply{
                                    putExtra("Page",1)
                                    startActivity(this)
                                }
                                finish()
                            }
                        }
                        override fun onFailure(e: IOException) {

                        }
                    })
                }
            })


            confirm.show()
        }
        binding.button14.setOnClickListener {
            val confirm = AlertDialog.Builder(this)
            confirm.setMessage("確認此機台無故障嗎?")
            confirm.setTitle("確認視窗")
            confirm.setNegativeButton("取消", null)
            confirm.setPositiveButton("確定",object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    mOkHttpUtil.put("https://tkudorm.site/repair", JSONObject("{'index':'${index}' , 'con' : 'usable'} "),object : OkHttpUtil.ICallback {
                        override fun onResponse(response: Response) {
                            val res = response.body?.string()
                            runOnUiThread{
                                onBackPressed()
                                Toast.makeText(this@DescribePage,JSONObject(res)["msg"].toString(),Toast.LENGTH_LONG).show()
                                finish()
                            }
                        }
                        override fun onFailure(e: IOException) {
                        }
                    })
                }

            })
            confirm.show()
            }
        }
    }
