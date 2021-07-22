package com.example.tkulife_pro.student.laundry.status.machineStatus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.tkulife_pro.R

class MachineDiagram : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView:View=inflater.inflate(R.layout.fragment_machine_diagram,container,false)
        rootView.findViewById<View>(R.id.close).setOnClickListener{
            dismiss()
        }
        return rootView
    }


}