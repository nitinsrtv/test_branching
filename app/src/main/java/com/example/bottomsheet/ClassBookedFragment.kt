package com.example.bottomsheet

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClassBookedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClassBookedFragment : BottomSheetDialogFragment() {
    private lateinit var up:String
    private lateinit var low:String
    private lateinit var upperText:TextView
    private lateinit var lowerText:TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_class_booked, container, false)

        upperText=view.findViewById(R.id.classConfirmation)
        lowerText=view.findViewById(R.id.textView8)
        val bundle=arguments
        val up= bundle?.getString("upperText")
        val low = bundle?.getString("lowerText")
        upperText.text=up
        lowerText.text=low
        val img=view.findViewById<ImageView>(R.id.imageView6)
        if(up=="Your class has been booked!"){
            img.setImageResource(R.drawable.ic_done)
        }else{
            img.setImageResource(R.drawable.ic_request_callback_)
        }
        val cross = view.findViewById<ImageView>(R.id.crossClass)

        cross.setOnClickListener{
            dismiss()
        }
        return view

    }

}