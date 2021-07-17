package com.example.tkulife_pro.student.laundry.fixreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.tkulife_pro.OkHttpUtil
import com.example.tkulife_pro.OkHttpUtil.Companion.mOkHttpUtil
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
        binding.spinner1.onItemSelectedListener = this
        binding.button.setOnClickListener {
            Log.d("fix",radioSelectType())
            Log.d("fix",stringCombination())
            var type=radioSelectType()
            var no=stringCombination()
            val depict=binding.editTextTextPersonName.text.toString()
            val json=JSONObject(
                "{'type':'${type}','no':'${no}','depict':'${depict}'}"
            )
            if(type!="未選擇"){
                binding.button.isEnabled=false
                mOkHttpUtil.post("https://tkudorm.site/repair",json,object: OkHttpUtil.ICallback{
                    override fun onResponse(response: Response) {
                        val res = response.body?.string()
                        Snackbar.make(it, JSONObject(res)["msg"].toString(), Snackbar.LENGTH_LONG).show()
                        runOnUiThread{
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

    private fun radioSelectType(): String {
        var res=""
        if (binding.radioButton.isChecked || binding.radioButton2.isChecked) {
            if (binding.radioButton.isChecked) {
                res="Washer"
            } else {
                res="Dryer"
            }
        } else {
            res="未選擇"
            Toast.makeText(this,"請選擇設備種類!",Toast.LENGTH_LONG).show()
        }
        return res
    }
    private fun stringCombination():String {  //spinner組合(棟+樓+機台編號)
        var res=""
        var array= arrayOf("A","B","C")
        val floor=binding.spinner1.selectedItemPosition
        val building=binding.spinner2.selectedItemPosition
        val id=binding.spinner3.selectedItemPosition
        if(floor==0){
            res="1F_${id}"
        }else {
            res = "${array[building]}-0${floor+1}_${id}"
        }
        return res
    }
}