package com.constantlearningdad.w23timetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.constantlearningdad.w23timetracker.databinding.ActivityCreateProjectBinding

class CreateProjectActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCreateProjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}