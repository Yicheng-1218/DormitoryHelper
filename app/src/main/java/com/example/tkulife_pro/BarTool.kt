package com.example.tkulife_pro

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class BarTool(private val activity: AppCompatActivity) {

    private val myBar by lazy {
        activity.supportActionBar?.apply {
            displayOptions=ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.abs_layout)
        }
    }

    private fun setBackgroundColor(colorHex:String){
        myBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(colorHex)))
    }
    private fun setBackgroundColor(colorRes:Int){
        myBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(activity,colorRes)))
    }

    private fun setBarTitle(title:String){
//        binding.barTitle.text=title
        myBar?.customView?.findViewById<TextView>(R.id.barTitle)?.text=title
    }

    fun setBundle(title: String,colorHex: String){
        setBackgroundColor(colorHex)
        setBarTitle(title)
    }
    fun setBundle(title: String,colorRes:Int){
        setBarTitle(title)
        setBackgroundColor(colorRes)
    }
}