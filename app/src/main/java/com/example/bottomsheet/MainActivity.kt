package com.example.bottomsheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.HtmlCompat


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mFragmentManager = supportFragmentManager





        val mentorBottomSheet = findViewById<Button>(R.id.button)
        val mentor_bottom_sheet = MentorBottomSheetFragment()
        mentorBottomSheet.setOnClickListener{
              mentor_bottom_sheet.show(supportFragmentManager,"BottomSheetDialog")
        }


        val CallbackBottomSheet = findViewById<Button>(R.id.button1)
        val callback_bottom_sheet= ClassBookedFragment()
        CallbackBottomSheet.setOnClickListener{

            val mBundle = Bundle()
            mBundle.putString("upperText","Connect with our Academic Counsellors and get personalised response")
            mBundle.putString("lowerText","Your request will be responded within 24-48hrs")
            callback_bottom_sheet.arguments= mBundle
            callback_bottom_sheet.show(supportFragmentManager,"BottomSheet")

        }



        val classBookedBottomSheet = findViewById<Button>(R.id.button2)
        val classbooked_bottom_sheet= ClassBookedFragment()


        classBookedBottomSheet.setOnClickListener {

            val mBundle = Bundle()
            mBundle.putString("upperText", "Your class has been booked!")
            mBundle.putString(
                "lowerText",
                "Your class has been booked for 22nd Jan, 8pm. Class link will be sent to your registered email id."
            )
            classbooked_bottom_sheet.arguments = mBundle
            classbooked_bottom_sheet.show(supportFragmentManager, "BottomSheet")


        }
        val aboutClassBottomSheet = findViewById<Button>(R.id.button3)
        val aboutclass_bottom_sheet = AboutClassFragment()
        aboutClassBottomSheet.setOnClickListener{
            aboutclass_bottom_sheet.show(supportFragmentManager,"BottomSheetDialog")
        }

    }
}