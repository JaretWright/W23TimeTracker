package com.constantlearningdad.w23timetracker

import android.content.Context
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

    override fun getItemCount(): Int {
        return projects.size
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}