package com.scaler.ui.ui.fragments

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager


import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.scaler.ui.databinding.FragmentPDFBottomSheetListDialogBinding
import com.scaler.ui.ui.adapters.PdfAdapter
import java.io.File

import java.io.FileOutputStream

import java.io.FileInputStream
import java.io.IOException












class PdfBottomSheetFragment : BottomSheetDialogFragment() {

    private var binding: FragmentPDFBottomSheetListDialogBinding? = null
    private val linearLayoutManger by lazy { LinearLayoutManager(context) }
    private var parcelFileDescriptor: ParcelFileDescriptor? = null
    private var pdfAdapter: PdfAdapter? = null
    private lateinit var filePDF: File
    private var fileDownloaded=false
    var downloadId: Long = 0


    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadId == id) {
                fileDownloaded=true
                initPdfViewer(filePDF)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPDFBottomSheetListDialogBinding.inflate(inflater, container, false)
        binding?.baseProgressBar?.visibility = View.VISIBLE
        setHasOptionsMenu(true)

        arguments?.getString(ALConstants.URL)?.let {
            beginDownload(it)
        }
        requireActivity().registerReceiver(onDownloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
        val heightRecyclerView = (resources.displayMetrics.heightPixels/100)*75
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heightRecyclerView)
        binding?.root?.layoutParams = lp
        binding?.imgClose?.setOnClickListener{
            dismiss()
        }
        beginDownload("https://orkustofnun.is/gogn/unu-gtp-sc/UNU-GTP-SC-19-0302.pdf")
        binding?.imgDownload?.setOnClickListener{
            if(fileDownloaded) {
                savePDF()
                Toast.makeText(requireContext(), "Downloaded", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "Not Yet Loaded", Toast.LENGTH_SHORT).show()
            }
        }
        binding?.imgShare?.setOnClickListener{
            if(fileDownloaded){
                sharePDF()
            }else{
                Toast.makeText(requireContext(), "Not Yet Loaded", Toast.LENGTH_SHORT).show()
            }
        }

        return binding!!.root
    }



    private fun initPdfViewer(pdfFile: File) {
        try {

            parcelFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            parcelFileDescriptor?.let {
                pdfAdapter = PdfAdapter(it, context)
            }
            binding?.apply {
                listPdf.visibility = View.VISIBLE
                baseProgressBar.visibility = View.GONE
                listPdf.layoutManager = linearLayoutManger
                listPdf.adapter = pdfAdapter
            }

        } catch (e: Exception) {
            pdfFile.delete()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        parcelFileDescriptor?.close()
        requireActivity().unregisterReceiver(onDownloadComplete)
        pdfAdapter?.clear()
        binding = null
    }

    private fun beginDownload(url: String) {
        val fileName: String = System.currentTimeMillis().toString()
        filePDF = File(context?.getExternalFilesDir(null)?.absolutePath, fileName)

        val request = DownloadManager.Request(Uri.parse(url))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE) // Visibility of the download Notification
            .setDestinationUri(Uri.fromFile(filePDF)) // Uri of the destination file
            .setTitle(fileName) // Title of the Download Notification
            .setDescription("Downloading") // Description of the Download Notification
            .setAllowedOverMetered(true) // Set if download is allowed on Mobile network
            .setAllowedOverRoaming(true) // Set if download is allowed on roaming network
        val downloadManager =
            activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?

        downloadManager?.let {
            downloadId =
                it.enqueue(request) // enqueue puts the download request in the queue.
        }


    }
    val fileName: String = System.currentTimeMillis().toString()
    val destinationPath =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val destination = File(destinationPath,fileName)
    private fun savePDF(){


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            try {
                FileUtils.copy( FileInputStream(filePDF.absoluteFile), FileOutputStream(destination))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }else{
            //check if permission is given or not, if given call copy else ask permission if granted call copy
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                // Requesting the permission
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
            } else {
                copy( filePDF.absoluteFile, destination)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            0 -> {
                copy(filePDF.absoluteFile,destination)
            }
        }
        }



    @Throws(IOException::class)
    fun copy(src: File?, dst: File?) {
        FileInputStream(src).use { `in` ->
            FileOutputStream(dst).use { out ->
                // Transfer bytes from in to out
                val buf = ByteArray(2048)
                var len: Int
                while (`in`.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
            }
        }
    }

    private fun sharePDF(){
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"  // "*/*" will accepts all types of files, if you want specific then change it on your need.
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(
                Intent.EXTRA_SUBJECT,
                "Sharing file from the AppName"
            )
            putExtra(
                Intent.EXTRA_TEXT,
                "Sharing file from the AppName with some description"
            )
            val fileURI = FileProvider.getUriForFile(
                context!!, context!!.packageName + ".provider",
                File(filePDF.absolutePath)
            )
            putExtra(Intent.EXTRA_STREAM, fileURI)
        }
        startActivity(shareIntent)
    }



    object ALConstants {
        const val URL = "url"

    }

}