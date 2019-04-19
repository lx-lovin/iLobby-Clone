package com.example.testapp

import java.util.*

data class SuspiciousUsersData(var downloadUrl:String,
                               var uuid:String) {
    constructor():this("", UUID.randomUUID().toString()){

    }
}