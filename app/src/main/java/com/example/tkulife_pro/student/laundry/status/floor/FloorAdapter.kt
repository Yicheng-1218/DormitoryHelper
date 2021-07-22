package com.example.tkulife_pro.student.laundry.status.floor

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tkulife_pro.databinding.FloorItemBinding

class FloorAdapter(private var itemClickListener: OnItemClick): RecyclerView.Adapter<FloorAdapter.ViewHolder>() {

    lateinit var machineData : HashMap<*,*>
    lateinit var machineType : String

    class ViewHolder(val view: FloorItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = FloorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.textView22.text = "${position+1}F"
        holder.view.textView23.text = "共有${getUsable(machineData)[position]}台可用"
        holder.view.floorItem.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return 6
    }

//    拆解資料計算可用台數
    private fun getUsable(data: HashMap<*,*>):ArrayList<Int>{
        val type = data[machineType] as HashMap<*,*>
        val total = arrayListOf(0,0,0,0,0,0)
        for (F in type.keys ){
            if(F!="1F"){
                F as String
                when (F[3]){
                    '2' -> {
                        for (i in type[F] as ArrayList<HashMap<*,*>>){
                            if(i["con"]=="usable"){
                                total[1]++
                            }
                        }
                    }
                    '3' -> {
                        for (i in type[F] as ArrayList<HashMap<*,*>>){
                            if(i["con"]=="usable"){
                                total[2]++
                            }
                        }
                    }
                    '4' -> {
                        for (i in type[F] as ArrayList<HashMap<*,*>>){
                            if(i["con"]=="usable"){
                                total[3]++
                            }
                        }
                    }
                    '5' -> {
                        for (i in type[F] as ArrayList<HashMap<*,*>>){
                            if(i["con"]=="usable"){
                                total[4]++
                            }
                        }
                    }
                    '6' -> {
                        for (i in type[F] as ArrayList<HashMap<*,*>>){
                            if(i["con"]=="usable"){
                                total[5]++
                            }
                        }
                    }
                }
            }else{
                for (i in type["1F"] as ArrayList<HashMap<*,*>>) {
                    if(i["con"]=="usable"){
                        total[0]++
                    }
                }
            }
        }
        Log.d("value", "總共$total")
        return total
    }

//    元素點擊介面
    interface OnItemClick{
        fun onItemClick(position: Int)
    }
}