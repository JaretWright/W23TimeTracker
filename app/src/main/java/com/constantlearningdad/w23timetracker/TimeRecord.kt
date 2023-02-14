package com.constantlearningdad.w23timetracker

import com.google.firebase.Timestamp

class TimeRecord (
    var activity: String? = null, //This will be to name the activity i.e. research, coding, testing, etc...
    var startTime: Timestamp? = null,
    var endTime: Timestamp?=null
        )