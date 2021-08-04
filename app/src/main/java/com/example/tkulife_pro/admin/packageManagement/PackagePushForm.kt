package com.example.tkulife_pro.admin.packageManagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.tkulife_pro.R
import com.example.tkulife_pro.databinding.PackageNotificationFormBinding

class PackagePushForm: Fragment() {

    private lateinit var binding: PackageNotificationFormBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= PackageNotificationFormBinding.inflate(layoutInflater)

        return binding.root
    }
}