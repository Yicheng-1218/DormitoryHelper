package com.example.tkulife_pro.admin.packageManagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.tkulife_pro.R

class PackagePushForm:DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView:View=inflater.inflate(R.layout.package_notification_form,container,false)

        rootView.findViewById<View>(R.id.button19).setOnClickListener{
            var id=R.id.package_id.toString()
            Toast.makeText(context,"已成功發送",Toast.LENGTH_LONG)
            dismiss()
        }

        return rootView
    }
}