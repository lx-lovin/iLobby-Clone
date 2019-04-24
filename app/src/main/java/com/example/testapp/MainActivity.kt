package com.example.testapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.widget.*
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URI
import java.util.concurrent.TimeUnit
import android.R.attr.bitmap
import android.R.attr.data
import android.graphics.BitmapFactory
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.firebase.database.*
import java.io.ByteArrayOutputStream
import java.util.*


class MainActivity : AppCompatActivity() {


    val countryNames = arrayOf(
        "Afghanistan",
        "Albania",
        "Algeria",
        "Andorra",
        "Angola",
        "Antarctica",
        "Argentina",
        "Armenia",
        "Aruba",
        "Australia",
        "Austria",
        "Azerbaijan",
        "Bahrain",
        "Bangladesh",
        "Belarus",
        "Belgium",
        "Belize",
        "Benin",
        "Bhutan",
        "Bolivia",
        "Bosnia And Herzegovina",
        "Botswana",
        "Brazil",
        "Brunei Darussalam",
        "Bulgaria",
        "Burkina Faso",
        "Myanmar",
        "Burundi",
        "Cambodia",
        "Cameroon",
        "Canada",
        "Cape Verde",
        "Central African Republic",
        "Chad",
        "Chile",
        "China",
        "Christmas Island",
        "Cocos (keeling) Islands",
        "Colombia",
        "Comoros",
        "Congo",
        "Cook Islands",
        "Costa Rica",
        "Croatia",
        "Cuba",
        "Cyprus",
        "Czech Republic",
        "Denmark",
        "Djibouti",
        "Timor-leste",
        "Ecuador",
        "Egypt",
        "El Salvador",
        "Equatorial Guinea",
        "Eritrea",
        "Estonia",
        "Ethiopia",
        "Falkland Islands (malvinas)",
        "Faroe Islands",
        "Fiji",
        "Finland",
        "France",
        "French Polynesia",
        "Gabon",
        "Gambia",
        "Georgia",
        "Germany",
        "Ghana",
        "Gibraltar",
        "Greece",
        "Greenland",
        "Guatemala",
        "Guinea",
        "Guinea-bissau",
        "Guyana",
        "Haiti",
        "Honduras",
        "Hong Kong",
        "Hungary",
        "India",
        "Indonesia",
        "Iran",
        "Iraq",
        "Ireland",
        "Isle Of Man",
        "Israel",
        "Italy",
        "Ivory Coast",
        "Jamaica",
        "Japan",
        "Jordan",
        "Kazakhstan",
        "Kenya",
        "Kiribati",
        "Kuwait",
        "Kyrgyzstan",
        "Laos",
        "Latvia",
        "Lebanon",
        "Lesotho",
        "Liberia",
        "Libya",
        "Liechtenstein",
        "Lithuania",
        "Luxembourg",
        "Macao",
        "Macedonia",
        "Madagascar",
        "Malawi",
        "Malaysia",
        "Maldives",
        "Mali",
        "Malta",
        "Marshall Islands",
        "Mauritania",
        "Mauritius",
        "Mayotte",
        "Mexico",
        "Micronesia",
        "Moldova",
        "Monaco",
        "Mongolia",
        "Montenegro",
        "Morocco",
        "Mozambique",
        "Namibia",
        "Nauru",
        "Nepal",
        "Netherlands",
        "New Caledonia",
        "New Zealand",
        "Nicaragua",
        "Niger",
        "Nigeria",
        "Niue",
        "Korea",
        "Norway",
        "Oman",
        "Pakistan",
        "Palau",
        "Panama",
        "Papua New Guinea",
        "Paraguay",
        "Peru",
        "Philippines",
        "Pitcairn",
        "Poland",
        "Portugal",
        "Puerto Rico",
        "Qatar",
        "Romania",
        "Russian Federation",
        "Rwanda",
        "Saint Barthélemy",
        "Samoa",
        "San Marino",
        "Sao Tome And Principe",
        "Saudi Arabia",
        "Senegal",
        "Serbia",
        "Seychelles",
        "Sierra Leone",
        "Singapore",
        "Slovakia",
        "Slovenia",
        "Solomon Islands",
        "Somalia",
        "South Africa",
        "Korea, Republic Of",
        "Spain",
        "Sri Lanka",
        "Saint Helena",
        "Saint Pierre And Miquelon",
        "Sudan",
        "Suriname",
        "Swaziland",
        "Sweden",
        "Switzerland",
        "Syrian Arab Republic",
        "Taiwan",
        "Tajikistan",
        "Tanzania",
        "Thailand",
        "Togo",
        "Tokelau",
        "Tonga",
        "Tunisia",
        "Turkey",
        "Turkmenistan",
        "Tuvalu",
        "United Arab Emirates",
        "Uganda",
        "United Kingdom",
        "Ukraine",
        "Uruguay",
        "United States",
        "Uzbekistan",
        "Vanuatu",
        "Holy See (vatican City State)",
        "Venezuela",
        "Viet Nam",
        "Wallis And Futuna",
        "Yemen",
        "Zambia",
        "Zimbabwe"
    )
    val countryAreaCodes = arrayOf(
        "93",
        "355",
        "213",
        "376",
        "244",
        "672",
        "54",
        "374",
        "297",
        "61",
        "43",
        "994",
        "973",
        "880",
        "375",
        "32",
        "501",
        "229",
        "975",
        "591",
        "387",
        "267",
        "55",
        "673",
        "359",
        "226",
        "95",
        "257",
        "855",
        "237",
        "1",
        "238",
        "236",
        "235",
        "56",
        "86",
        "61",
        "61",
        "57",
        "269",
        "242",
        "682",
        "506",
        "385",
        "53",
        "357",
        "420",
        "45",
        "253",
        "670",
        "593",
        "20",
        "503",
        "240",
        "291",
        "372",
        "251",
        "500",
        "298",
        "679",
        "358",
        "33",
        "689",
        "241",
        "220",
        "995",
        "49",
        "233",
        "350",
        "30",
        "299",
        "502",
        "224",
        "245",
        "592",
        "509",
        "504",
        "852",
        "36",
        "91",
        "62",
        "98",
        "964",
        "353",
        "44",
        "972",
        "39",
        "225",
        "1876",
        "81",
        "962",
        "7",
        "254",
        "686",
        "965",
        "996",
        "856",
        "371",
        "961",
        "266",
        "231",
        "218",
        "423",
        "370",
        "352",
        "853",
        "389",
        "261",
        "265",
        "60",
        "960",
        "223",
        "356",
        "692",
        "222",
        "230",
        "262",
        "52",
        "691",
        "373",
        "377",
        "976",
        "382",
        "212",
        "258",
        "264",
        "674",
        "977",
        "31",
        "687",
        "64",
        "505",
        "227",
        "234",
        "683",
        "850",
        "47",
        "968",
        "92",
        "680",
        "507",
        "675",
        "595",
        "51",
        "63",
        "870",
        "48",
        "351",
        "1",
        "974",
        "40",
        "7",
        "250",
        "590",
        "685",
        "378",
        "239",
        "966",
        "221",
        "381",
        "248",
        "232",
        "65",
        "421",
        "386",
        "677",
        "252",
        "27",
        "82",
        "34",
        "94",
        "290",
        "508",
        "249",
        "597",
        "268",
        "46",
        "41",
        "963",
        "886",
        "992",
        "255",
        "66",
        "228",
        "690",
        "676",
        "216",
        "90",
        "993",
        "688",
        "971",
        "256",
        "44",
        "380",
        "598",
        "1",
        "998",
        "678",
        "39",
        "58",
        "84",
        "681",
        "967",
        "260",
        "263"
    )
    lateinit var mAuth:FirebaseAuth
    lateinit var spinner: Spinner
    lateinit var databaseReference:DatabaseReference
    lateinit var phoneNmber: EditText
    lateinit var sendOtp: Button
    lateinit var profilePhoto: ImageView
    var dataImage:ByteArray = byteArrayOf()


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.countrySelector)
        databaseReference = FirebaseDatabase.getInstance().reference.child("visitors")
        phoneNmber = findViewById(R.id.phoneNumber)
        sendOtp = findViewById(R.id.sendOtp)
        profilePhoto = findViewById(R.id.profilePhoto)
        mAuth = FirebaseAuth.getInstance()
        spinner.adapter = ArrayAdapter<String>(this, R.layout.color_spinner, countryNames)


        profilePhoto.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),10)
            }
            /*if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
            }else{

                var intent:Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent,1)*/
           else {
                var intent: Intent = Intent(this@MainActivity, CameraActivity::class.java)
                startActivityForResult(intent, 2)
            }
        }



        sendOtp.setOnClickListener {

            if(dataImage.isEmpty()){
                Toast.makeText(this, "Please Select Photo", Toast.LENGTH_LONG).show()
            }else if (phoneNmber.text.isEmpty()) {
                Toast.makeText(this, "Please Enter Phone Number", Toast.LENGTH_LONG).show()
            }else if(phoneNmber.text.toString().length < 10){
                 Toast.makeText(this, "Please Enter Valid Phone Number", Toast.LENGTH_LONG).show()
             }
            else{


                databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var databasePhone:String = ""
                        var databasePhone1:String = ""
                        var databasevisit:String = ""
                        var databaseDownloadurl:String = ""
                        var databaseuuid:String = ""
                        for (ds: DataSnapshot in p0.children) {
                            if(phoneNmber.text.toString() == ds.getValue(VisitorsData::class.java)!!.phone.toString()){
                                databasePhone=phoneNmber.text.toString()
                                databaseDownloadurl = ds.getValue(VisitorsData::class.java)!!.downloadUrl.toString()
                                databasePhone1 =  ds.getValue(VisitorsData::class.java)!!.phone.toString()
                                databaseuuid = ds.getValue(VisitorsData::class.java)!!.uuid.toString()
                                databasevisit = (ds.getValue(VisitorsData::class.java)!!.visit.toInt()+1).toString()
                                Toast.makeText(this@MainActivity,"Welcome "+(ds.getValue(VisitorsData::class.java)!!.visit.toInt()+1).toString()+" time",Toast.LENGTH_LONG).show()
                            }
                        }
                        if(databasePhone==phoneNmber.text.toString()){
                            var visitorsData:VisitorsData = VisitorsData(databaseDownloadurl,databaseuuid.toString(),databasevisit,databasePhone1)
                            databaseReference.child(databasePhone1).setValue(visitorsData)
                            var intent:Intent  = Intent(this@MainActivity,HomeActivity::class.java)
                            intent.putExtra("phone",""+phoneNmber.text)
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }else if(databasePhone==""){

                            var intent:Intent = Intent(this@MainActivity,OtpActivity::class.java)
                            intent.putExtra("phoneNumber","+"+countryAreaCodes[spinner.selectedItemPosition]+phoneNmber.text)
                            intent.putExtra("Image",dataImage)
                            intent.putExtra("wcode",""+phoneNmber.text)
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }

                })

            }

        }
                    /**/
    }


    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==1 && data!=null && resultCode== Activity.RESULT_OK){
            var selectedImage:Bitmap = data.extras.get("data") as Bitmap
            profilePhoto.setImageBitmap(selectedImage)
            val baos = ByteArrayOutputStream()
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            dataImage = baos.toByteArray()
        }
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==2){
            if (resultCode==Activity.RESULT_OK){
                dataImage = data!!.getByteArrayExtra("bytes")
                var bmp:Bitmap = BitmapFactory.decodeByteArray(dataImage, 0, dataImage.size)
                profilePhoto.setImageBitmap(Bitmap.createScaledBitmap(bmp, profilePhoto.getWidth(),
                    profilePhoto.getHeight(), false));


            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){

            if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                var intent:Intent = Intent(this@MainActivity,CameraActivity::class.java)
                startActivityForResult(intent,2)

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
}
