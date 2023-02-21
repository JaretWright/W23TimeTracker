package com.constantlearningdad.w23timetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.constantlearningdad.w23timetracker.databinding.ActivityCreateProjectBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class CreateProjectActivity : AppCompatActivity(), ProjectAdapter.ProjectItemListener {
    private lateinit var binding : ActivityCreateProjectBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.createProjectButton.setOnClickListener {
            //if the fields are populated, create a Project object
            //and store it in firestore
            var projectName = binding.projectNameEditText.text.toString()
            var description = binding.descriptionEditText.text.toString()

            if (projectName.isNotEmpty() && description.isNotEmpty())
            {
                var uID = auth.currentUser.uid

                //create a project and send to firestore
                var project = Project(projectName, description, uID, ArrayList())

                //connect to Firebase-Firestore
                //if the collection doesn't exist, it will add it
                //if the collection exists, it will access it
                val db = FirebaseFirestore.getInstance().collection("projects")

                var documentId = projectName+"-"+uID

                db.document(documentId).set(project)
                    .addOnSuccessListener {
                        Toast.makeText(this,"DB Updated",Toast.LENGTH_LONG).show()
                        binding.projectNameEditText.text.clear()
                        binding.descriptionEditText.text.clear()

                        //read from the DB and log the projects
                        db.get().addOnSuccessListener { collection ->
                            for (document in collection)
                            {
                                Log.i("Firestore","${document.id}=>${document.data}")
                            }
                        }
                            .addOnFailureListener{}

                    }
                    .addOnFailureListener { exception -> Log.w("DB_Issue",exception.localizedMessage) }
            }
            else
            {
                Toast.makeText(this, "name and description are both required",Toast.LENGTH_LONG).show()
            }
        }

        //This will be used to connect the RecyclerView, adapeter and viewModel together
        val viewModel : ProjectViewModel by viewModels()
        viewModel.getProjects().observe(this, {
            binding.recyclerView.adapter = ProjectAdapter(this,it,this)
        })
    }

    override fun projectSelected(project: Project) {
        Log.i("Project_Selected", "$project")
    }
}