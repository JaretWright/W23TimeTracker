package com.constantlearningdad.w23timetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.constantlearningdad.w23timetracker.databinding.ActivityLogTimeActiviyBinding

class LogTimeActiviy : AppCompatActivity() {
    private lateinit var binding : ActivityLogTimeActiviyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogTimeActiviyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var documentID = intent.getStringExtra("documentID")
        Log.i("documentID",documentID.toString())

        val viewModel : ProjectViewModel by viewModels()
        viewModel.getProjects()
    }
}