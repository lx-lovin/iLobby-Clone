package com.example.testapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    lateinit var phoneNmber:String
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        databaseReference = FirebaseDatabase.getInstance().reference.child("visitors")

        phoneNmber = intent.getStringExtra("phone")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (ds: DataSnapshot in p0.children) {
                    if(phoneNmber == ds.getValue(VisitorsData::class.java)!!.phone.toString()){
                        visit.text = ("Visits : "+ds.getValue(VisitorsData::class.java)!!.visit.toString())
                        uuid.text = ("Your ID : "+ds.getValue(VisitorsData::class.java)!!.uuid.toString())
                        Picasso.get().load(ds.getValue(VisitorsData::class.java)!!.downloadUrl.toString()).into(userPhoto);
                    }
                }
            }

        })


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
}
