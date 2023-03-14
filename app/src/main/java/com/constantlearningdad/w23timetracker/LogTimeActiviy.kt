package com.constantlearningdad.w23timetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.constantlearningdad.w23timetracker.databinding.ActivityLogTimeActiviyBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.sql.Time

class LogTimeActiviy : AppCompatActivity() {
    private lateinit var binding : ActivityLogTimeActiviyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogTimeActiviyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var documentID = intent.getStringExtra("documentID")
        Log.i("documentID",documentID.toString())

        //create a list of Projects to populate the Project Spinner
        var projects : MutableList<Project> = ArrayList()

        val userID = FirebaseAuth.getInstance().currentUser!!.uid

        if (userID == null)
        {
            finish()
            startActivity(Intent(this, SigninActivity::class.java))
        }

        //connect to Firestore and get list of projects
        val db = FirebaseFirestore.getInstance().collection("projects")

        //use the adapater to connect the list of projects with the Spinner
        val adapter = ArrayAdapter(applicationContext, R.layout.item_spinner, projects)
        binding.projectSpinner.adapter = adapter

        var projectSelected = Project()

        val viewModel : ProjectViewModel by viewModels()
        viewModel.getProjects().observe(this, {
            projects.addAll(it)
            documentID?.let{
                    for (project in projects)
                    {
                        var projectIdentifier = project!!.projectName+"-"+project.uid
                        if (projectIdentifier.equals(documentID))
                            projectSelected = project
                    }
                    binding.projectSpinner.setSelection(projects.indexOf(projectSelected))
            }
            adapter.notifyDataSetChanged()
        })

        //create variables to store the start, stop and category
        var startTime : Timestamp? = null
        var stopTime : Timestamp? = null
        var category : String? = null

        binding.startButton.setOnClickListener {
            if (startTime == null)
            {
                startTime = Timestamp.now()
                binding.startTextView.text = startTime!!.toDate().toString()
            }
        }

        //setup the stop time button
        binding.stopButton.setOnClickListener {
            if (startTime != null && binding.spinner.selectedItemPosition>0)
            {
                category = binding.spinner.selectedItem.toString()
                stopTime = Timestamp.now()
                binding.stopTextView.text = stopTime!!.toDate().toString()

                //Create a time record
                val timeRecord = TimeRecord(category,startTime,stopTime)
            }
            else
                Toast.makeText(this,"start time and catgeory are required",Toast.LENGTH_LONG).show()
        }


    }
}