package com.constantlearningdad.w23timetracker

class Project (
    var projectName : String? = null,
    var description : String? = null,
    var uid : String? = null,
    var timeRecords : ArrayList<TimeRecord>? = null
        )