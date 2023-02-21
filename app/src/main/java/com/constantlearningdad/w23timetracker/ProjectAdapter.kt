package com.constantlearningdad.w23timetracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProjectAdapter (val context : Context,
                      val projects : List<Project>) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>(){

    /**
     * This class is used to allow us to connect / access the elements in the item_project layout file
     */
    inner class ProjectViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val projectTextView = itemView.findViewById<TextView>(R.id.projectTextView)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.descriptionTextView)
    }


    /**
     * This connects to the item_project layout file and "inflates" the objects (similar to how ViewBinding inflates the
     * view elements)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = inflator.inflate(R.layout.item_project, parent, false)
        return ProjectViewHolder(view)
    }

    /**
     * Bind (or connect) the data from our projects with the view.  This is populating
     * the information into the TextView objects in item_project.xml
     */
    override fun onBindViewHolder(viewHolder: ProjectViewHolder, position: Int) {
        val project = projects[position]
        with (viewHolder){
            projectTextView.text = project.projectName
            descriptionTextView.text = project.description
        }
    }

    override fun getItemCount(): Int {
        return projects.size
    }


}