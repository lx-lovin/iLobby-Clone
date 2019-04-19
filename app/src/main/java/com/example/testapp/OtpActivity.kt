package com.example.testapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_otp.*
import java.util.concurrent.TimeUnit
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class OtpActivity : AppCompatActivity() {

    lateinit var verificationCode:String
    lateinit var mAuth:FirebaseAuth
    lateinit var storage: StorageReference
    lateinit var dataImage:ByteArray
    lateinit var phoneNumberr:String
    lateinit var phoneNumberWCode:String
    lateinit var databaseReference: DatabaseReference
    lateinit var databaseReference1: DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        var intent:Intent = getIntent()
        phoneNumberWCode = intent.getStringExtra("wcode")
        phoneNumberr= intent.getStringExtra("phoneNumber")
        dataImage = intent.getByteArrayExtra("Image")
        mAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance().reference.child("userphotos")
        databaseReference = FirebaseDatabase.getInstance().reference.child("visitors")
        databaseReference1 = FirebaseDatabase.getInstance().reference.child("suspicious_users")
        sendVerficationCode(phoneNumberr)

        verify.setOnClickListener {
            VerifyCode(code.text.toString())
        }

        againPhoneNumber.setOnClickListener {
            var intent = Intent(this@OtpActivity,MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }


    private fun sendVerficationCode(s: String) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            s,
            30,
            TimeUnit.SECONDS,
            this,
            mCallBack
        )

    }


    //VERIFY CODE



    fun VerifyCode(code:String){
        var credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(verificationCode,code)
        signInWithCredentials(credential)

    }


    //SIGNIN WITH PHONE NUMBER


    private fun signInWithCredentials(credential: PhoneAuthCredential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                var intent:Intent  = Intent(this,HomeActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("phone",phoneNumberWCode)
                startActivity(intent)
                storage.child(phoneNumberr).putBytes(dataImage).addOnSuccessListener {
                    var downloadUrl = it.storage.downloadUrl.addOnSuccessListener {
                        var visitorsData:VisitorsData = VisitorsData(it.toString(),UUID.randomUUID().toString(),"1",phoneNumberWCode)
                        databaseReference.child(phoneNumberWCode).setValue(visitorsData)
                    }
                }
            }else{
                Toast.makeText(this@OtpActivity,"Incorrect OTP", Toast.LENGTH_LONG).show()
                storage.child(phoneNumberr).putBytes(dataImage).addOnSuccessListener {
                    var downloadUrl = it.storage.downloadUrl.addOnSuccessListener {
                        var suspiciousUsersData = SuspiciousUsersData(it.toString(),UUID.randomUUID().toString())
                        databaseReference1.child(phoneNumberWCode).setValue(suspiciousUsersData)
                    }
                }
            }
        }
    }


    //CHECKING CODE COMPLETE/VERIFICATION


    var mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(p0: String?, p1: PhoneAuthProvider.ForceResendingToken?) {
                super.onCodeSent(p0, p1)

                verificationCode = p0!!
            }


            override fun onVerificationCompleted(p0: PhoneAuthCredential?) {
               var code:String= p0!!.smsCode.toString()
                VerifyCode(code)


            }

            override fun onVerificationFailed(p0: FirebaseException?) {

                Toast.makeText(this@OtpActivity,p0!!.message.toString(), Toast.LENGTH_LONG).show()
            }

        }

}

