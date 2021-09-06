package com.example.tkulife_pro.admin.packageManagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.tkulife_pro.OkHttpUtil
import com.example.tkulife_pro.databinding.PackageNotificationFormBinding
import com.example.tkulife_pro.OkHttpUtil.Companion.mOkHttpUtil
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class PackagePushForm(private val userData:HashMap<*,*>): DialogFragment() {

    private lateinit var binding: PackageNotificationFormBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= PackageNotificationFormBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }


    private fun initView(){
//        loading圖示
        binding.progressBar5.isVisible=false
//        床號
        binding.bedNumber.text=userData["room_bed"].toString()
//        學號
        binding.stdNumber.text=userData["uid"].toString()
//        姓名
        binding.stdName.text=userData["name"].toString()
//        發送按鈕
        binding.button19.setOnClickListener {
            sendPackage()
        }
    }

//    發送包裹
    private fun sendPackage(){
        binding.progressBar5.isVisible=true
        binding.button19.isEnabled=false
        val pid: String =binding.packageId.editableText.toString()
        if (pid.isNotEmpty()){
            val body=JSONObject("{'room_bed':'${userData["room_bed"]}','pid':'$pid','token':'${userData["token"]}'}")
            mOkHttpUtil.post("https://tkudorm.site/package",body,object :OkHttpUtil.ICallback{
                override fun onResponse(response: Response) {
                    val res=response.body?.string()
                    val json=JSONObject(res!!)
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(),"${json["msg"]}",Toast.LENGTH_SHORT).show()
                        binding.progressBar5.isVisible=false
                        binding.button19.isEnabled=true
                    }
                }

                override fun onFailure(e: IOException) {

                }

            })
        }

    }

}