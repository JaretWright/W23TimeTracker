package com.constantlearningdad.w23timetracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

//In Java we would write it as "public class ProjectViewModel extends ViewModel{} "
class ProjectViewModel : ViewModel(){
    private var projects = MutableLiveData<List<Project>>()

    //Connect to the Firestore DB and update "projects" with all the projects for the
    //logged in user
    init{
        val userID = Firebase.auth.currentUser.uid

        val db = FirebaseFirestore.getInstance().collection("projects")
            .whereEqualTo("uid", userID)
            .orderBy("projectName")
            .addSnapshotListener{ documents, exception ->
                if (exception != null)
                {
                    Log.w("DB_Response","Listen failed ${exception.code}")
                    return@addSnapshotListener
                }
                documents?.let { documents
                    val projectList = ArrayList<Project>()
                    for (document in documents)
                    {
                        Log.i("DB_Response","${document.data}")
                        val project = document.toObject(Project::class.java)
                        projectList.add(project)
                    }
                    projects.value = projectList
                }
            }
    }

    //returns the list of projects in the system for the logged in user
    fun getProjects() : LiveData<List<Project>>
    {
        return projects
    }
}