package com.example.bottomsheet

import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment



class MentorBottomSheetFragment : BottomSheetDialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_mentor_bottom_sheet, container, false)

        val cross = view.findViewById<ImageView>(R.id.closeMBS)
        val tvProdDesc = view.findViewById<TextView>(R.id.about_mentor_text)
        val longDescription = """
            Worked with Mark Zuckerberg and led the team that built Facebook Messenger.
            Responsible for setting up Facebook’s office outside the US. 
            A star coder who represented India in the ICPC World Finals twice.
            """.trimIndent()
        val arr = longDescription.split("\n").toTypedArray()
        val bulletGap = dp(10).toInt()
        val ssb = SpannableStringBuilder()
        for (i in arr.indices) {
            val line = arr[i]
            val ss = SpannableString(line)
            ss.setSpan(BulletSpan(bulletGap), 0, line.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            ssb.append(ss)
            //avoid last "\n"
            if (i + 1 < arr.size) ssb.append("\n\n")
        }
        tvProdDesc.text = ssb
        cross.setOnClickListener{
            dismiss()
        }
        return view
    }
    private fun dp(dp: Int): Float {
        return resources.displayMetrics.density * dp
    }


}