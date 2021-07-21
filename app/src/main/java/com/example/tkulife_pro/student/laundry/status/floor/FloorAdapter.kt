package com.example.tkulife_pro.student.laundry.status.floor

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
    private fun getUsable(data: HashMap<*,*>):ArrayList<Int>{
        val type = data[machineType] as HashMap<*,*>
        val total = arrayListOf<Int>(0,0,0,0,0,0)
        val floor1 = type["1F"] as ArrayList<HashMap<*,*>>
        for (F in type.keys){
            if(F!="1F"){
                F as String
                when (F[3]){
                    '2' -> {
                        for (i in type[F] as ArrayList<HashMap<*,*,>>){
                            if(i["con"]=="usable"){
                                total[1]++
                            }
                        }
                    }
                    '3' -> {
                        for (i in type[F] as ArrayList<HashMap<*,*,>>){
                            if(i["con"]=="usable"){
                                total[2]++
                            }
                        }
                    }
                    '4' -> {
                        for (i in type[F] as ArrayList<HashMap<*,*,>>){
                            if(i["con"]=="usable"){
                                total[3]++
                            }
                        }
                    }
                    '5' -> {
                        for (i in type[F] as ArrayList<HashMap<*,*,>>){
                            if(i["con"]=="usable"){
                                total[4]++
                            }
                        }
                    }
                    '6' -> {
                        for (i in type[F] as ArrayList<HashMap<*,*,>>){
                            if(i["con"]=="usable"){
                                total[5]++
                            }
                        }
                    }
                }
            }else{
                for (i in floor1) {
                    if(i["con"]=="usable"){
                        total[0]++
                    }
                }
            }
        }
        Log.d("value","總共"+total)
        return total
    }
    interface OnItemClick{
        fun onItemClick(position: Int)
    }
}