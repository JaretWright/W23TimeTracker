package com.constantlearningdad.w23timetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.constantlearningdad.w23timetracker.databinding.ActivityLogTimeActiviyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

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

        db.whereEqualTo("uid", userID)
            .orderBy("projectName")
            .get()
            .addOnSuccessListener {
                projects.add(Project(projectName = "Choose a Project"))

                //loop over all the projects returned from Firestore and covert to Project objects
                for (document in it)
                {
                    val project = document.toObject(Project::class.java)
                    projects.add(project)
                }

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
        }
    }
}