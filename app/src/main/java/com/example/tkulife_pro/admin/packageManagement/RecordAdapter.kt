package com.example.tkulife_pro.admin.packageManagement

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.databinding.RecordItemBinding
import org.json.JSONArray
import org.json.JSONObject

class RecordAdapter() : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    private lateinit var binding : RecordItemBinding
    var packageList = ArrayList<HashMap<*, *>>()
        set(value) {
            field=value
            notifyDataSetChanged()
        }
    var mode:Int=0
        set(value) {
            field=value
            notifyDataSetChanged()
        }
    class ViewHolder(val view: RecordItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= RecordItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(mode){
            0->{
                setViewText(packageList,position,holder)
            }
            1->{
                setViewText(sortByTaken(true,packageList),position,holder)
            }
            2->{
                setViewText(sortByTaken(false,packageList),position,holder)
            }
        }
    }


    override fun getItemCount(): Int {
        var len=0
        when(mode){
            0->{
                len= packageList.size
            }
            1->{
                len= sortByTaken(true,packageList).size
            }
            2->{
                len= sortByTaken(false,packageList).size
            }
        }
        return len
    }

    @SuppressLint("SetTextI18n")
    private fun setViewText(list:ArrayList<HashMap<*,*>>, position: Int, holder: ViewHolder){
        val pkg=list[(list.size-1)-position]
        when(pkg["taken"]){
            true->{
                holder.view.takenText.apply {
                    text="已領"
                    setTextColor(Color.parseColor("#0099e5"))
                }
            }
            false->{
                holder.view.takenText.apply {
                    text="未領"
                    setTextColor(Color.parseColor("#ff4c4c"))
                }
            }
        }
        holder.view.pidText.text="${pkg["roomID"]}   後三碼:${pkg["pid"]}"
    }


    private fun sortByTaken(taken:Boolean,packageList:ArrayList<HashMap<*,*>>):ArrayList<HashMap<*,*>>{
        return ArrayList<HashMap<*,*>>().also {
            packageList.onEach { element->
                if (element["taken"] as Boolean == taken) {
                    it.add(element)
                }
            }
        }
    }
}
