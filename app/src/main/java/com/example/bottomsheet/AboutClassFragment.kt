package com.example.bottomsheet

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.DrawableMarginSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AboutClassFragment : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_about_class, container, false)

        val cL = view.findViewById<ConstraintLayout>(R.id.mainConstraint)
        val heightRecyclerView = (resources.displayMetrics.heightPixels/100)*75
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heightRecyclerView)
        cL.layoutParams = lp

        // (What is Academy Details Text)
        val cText1=view.findViewById<TextView>(R.id.whatIsAcademyDetails)
        val longDescription = """
            A program for engineers to master Problem Solving & System Design
            A structured, flexible & guided learning program
            """.trimIndent()
        val correct =requireContext().resources.getDrawable(R.drawable.ic_correct)
        val formattedText1 = textFormatter(longDescription,correct)
        cText1.text = formattedText1
        //

        // (Next Batch Text)
        val cText2=view.findViewById<TextView>(R.id.nextBatchText)
        val lD = """
            Next batch starts Mid February
            """.trimIndent()
        val clock =requireContext().resources.getDrawable(R.drawable.ic_clock_1)
        val formattedText2 = textFormatter(lD,clock)
        cText2.text = formattedText2
        //

        // Benefits of Live Class
        val cText3=view.findViewById<TextView>(R.id.LClassDetails)
        val lD1 = """
            It would be a 60 mins session with the co-founders
            Get to know about the industry
            Get to know how to get a fair career trajectory
            """.trimIndent()
        val formattedText3 = textFormatter(lD1,correct)
        cText3.text = formattedText3

        val cross = view.findViewById<ImageView>(R.id.crossMentor2)
        cross.setOnClickListener{
            dismiss()
        }

        /*val rgp = view.findViewById<RadioGroup>(R.id.radioGroup)
        val buttons = 2
        for (i in 1..buttons) {
            var rbn = RadioButton(requireContext())
            rbn.id = View.generateViewId()
            rbn.layoutParams.height= ViewGroup.LayoutParams.MATCH_PARENT
            rbn.layoutParams.width=ViewGroup.LayoutParams.WRAP_CONTENT
            rbn.text="Saturday, 23rd Jan   |   8:00 pm - 11:00 pm"
            rgp.addView(rbn)
        }*/

        return view
    }
    private fun textFormatter(s: String, v:Drawable):SpannableStringBuilder{
        val arr = s.split("\n").toTypedArray()
        val bulletGap = dp(8).toInt()
        val ssb = SpannableStringBuilder()
        for (i in arr.indices) {
            val line = arr[i]
            val ss = SpannableString(line)
            v.setBounds(0, 0, 20,20)
            ss.setSpan(DrawableMarginSpan(v,bulletGap), 0, line.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            ssb.append(ss)
            //avoid last "\n"
            if (i + 1 < arr.size) ssb.append("\n\n")
        }
        ssb.append("\n")
        return ssb
    }
    private fun dp(dp: Int): Float {
        return resources.displayMetrics.density * dp
    }

}