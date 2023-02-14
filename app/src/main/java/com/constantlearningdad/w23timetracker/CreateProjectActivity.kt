package com.constantlearningdad.w23timetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.constantlearningdad.w23timetracker.databinding.ActivityCreateProjectBinding
import com.google.firebase.firestore.FirebaseFirestore

class CreateProjectActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCreateProjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createProjectButton.setOnClickListener {
            //if the fields are populated, create a Project object
            //and store it in firestore
            var projectName = binding.projectNameEditText.text.toString()
            var description = binding.descriptionEditText.text.toString()

            if (projectName.isNotEmpty() && description.isNotEmpty())
            {
                //create a project and send to firestore
                var project = Project(projectName, description)

                //connect to Firebase-Firestore
                //if the collection doesn't exist, it will add it
                //if the collection exists, it will access it
                val db = FirebaseFirestore.getInstance().collection("projects")

                //save the project as a document
                val id = db.document().id
                project.id = id

                db.document().set(project)
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
    }
}