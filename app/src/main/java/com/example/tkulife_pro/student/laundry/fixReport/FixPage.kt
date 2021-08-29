package com.example.tkulife_pro.student.laundry.fixReport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.tkulife_pro.BarTool
import com.example.tkulife_pro.OkHttpUtil
import com.example.tkulife_pro.OkHttpUtil.Companion.mOkHttpUtil
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.ActivityFixPageBinding
import com.google.android.material.snackbar.Snackbar
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class FixPage : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityFixPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFixPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
//        設定BAR
        BarTool(this).setBundle("我要報修", R.color.barBlue)

//        新增spinner的選擇監聽
        binding.spinner1.onItemSelectedListener = this

//        卻定報修按鈕
        binding.button.setOnClickListener {
            val type=radioSelectType()
            val no=stringCombination()
            val depict=binding.detailBox.text.toString()

//            發送請求資料
            val json=JSONObject("{'type':'${type}','no':'${no}','depict':'${depict}'}")

//            如果DataValid發送請求
            if(type!="未選擇"){
//                請求過程關閉按鈕
                binding.button.isEnabled=false

//                學生端報修post
                mOkHttpUtil.post("https://tkudorm.site/repair",json,object: OkHttpUtil.ICallback{
                    override fun onResponse(response: Response) {
                        val res = response.body?.string()
//                        顯示server回傳結果
                        Snackbar.make(it, JSONObject(res!!)["msg"].toString(), Snackbar.LENGTH_LONG).show()

//                        UI線程
                        runOnUiThread{
//                            請求完成開啟按鈕
                            binding.button.isEnabled=true
                        }
                    }

                    override fun onFailure(e: IOException) {

                    }
                })
            }

        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//        如果選擇1F就只有洗衣場選項
        if (p2 == 0) {
            binding.spinner2.adapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("洗衣場"))
            binding.spinner3.adapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, (1..16).toList())
        } else {
            binding.spinner2.adapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("一館", "二館", "三館"))
            binding.spinner3.adapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, (1..3).toList())
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

//    檢查radioButton選擇種類
    private fun radioSelectType(): String {
        val res: String
        if (binding.radioButton.isChecked || binding.radioButton2.isChecked) {
            res = if (binding.radioButton.isChecked) {
                "Washer"
            } else {
                "Dryer"
            }
        } else {
            res="未選擇"
            Toast.makeText(this,"請選擇設備種類!",Toast.LENGTH_LONG).show()
        }
        return res
    }

    //spinner組合(棟+樓+機台編號)
    private fun stringCombination():String {
        val res: String
        val array= arrayOf("A","B","C")
        val floor=binding.spinner1.selectedItemPosition
        val building=binding.spinner2.selectedItemPosition
        val id=binding.spinner3.selectedItemPosition
        res = if(floor==0){
            "1F_${id}"
        }else {
            "${array[building]}-0${floor+1}_${id}"
        }
        return res
    }
}