package com.example.testapp

import java.util.*

data class VisitorsData(var downloadUrl:String,
                        var uuid:String,
                        var visit:String,
                        var phone:String) {
    constructor():this("", "","",""){

    }
}