package com.scaler.ui.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.scaler.ui.R
import com.scaler.ui.utils.PdfBitmapPool

class PdfAdapter(pdfParcelDescriptor: ParcelFileDescriptor, context : Context?) : RecyclerView.Adapter<PdfAdapter.PDFPageViewHolder>() {

    private val tag = "PdfAdapter"
    private var bitmapPool: PdfBitmapPool? = null
    private val pdfRenderer: PdfRenderer = PdfRenderer(pdfParcelDescriptor)

    init {
        context?.let {
            bitmapPool = PdfBitmapPool(pdfRenderer, Bitmap.Config.ARGB_8888,
                it.resources.displayMetrics.densityDpi)
        }
    }

    override fun getItemCount(): Int {
        Log.i(tag, "page count is "+pdfRenderer.pageCount)
        return pdfRenderer.pageCount
    }

    override fun onBindViewHolder(holder: PDFPageViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PDFPageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_p_d_f_bottom_sheet_list_dialog_item, parent, false)
        return PDFPageViewHolder(view)
    }

    fun clear() {
        pdfRenderer.close()
        bitmapPool?.bitmaps?.clear()
    }


    inner class PDFPageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(pagePosition: Int) {
            val imgPage = view.findViewById<ImageView>(R.id.pageImgView)
            imgPage.setImageBitmap(bitmapPool?.getPage(pagePosition))
            bitmapPool?.loadMore(pagePosition)
        }
    }
}

