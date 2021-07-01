package com.example.tkulife_pro.Student.Laundry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.R
import org.json.JSONArray
import org.json.JSONObject

    class L_Adapter(): RecyclerView.Adapter<L_Adapter.ViewHolder>(){
        lateinit var jsonArray: JSONArray

        class ViewHolder(v: View): RecyclerView.ViewHolder(v){
            val w_number=v.findViewById<TextView>(R.id.number)
            val w_status=v.findViewById<TextView>(R.id.status)
        }
        override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
            val v= LayoutInflater.from(viewGroup.context).inflate(R.layout.activity_ladapter,viewGroup,false)
            return ViewHolder(v)
        }

        override fun getItemCount() = jsonArray.length()

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.w_number.text=washers[position]._number.toString()
//        holder.w_status.text=washers[position]._status
            val array=jsonArray[position] as JSONObject
            holder.w_number.text=array.get("id").toString()
            holder.w_status.text=array.get("condition").toString()
        }
}