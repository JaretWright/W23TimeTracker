package com.constantlearningdad.w23timetracker

class Project (
    var projectName : String? = null,
    var description : String? = null,
    var uid : String? = null,
    var timeRecords : ArrayList<TimeRecord>? = null
        ){

    override fun toString(): String {
       if (projectName != null)
           return projectName!!
        return "undefined"
    }
}