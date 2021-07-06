package dev.pulkit.gocorona.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dev.pulkit.gocorona.R
import kotlinx.android.synthetic.main.fragment_x_ray_scan.*

class XRayScanFragment : Fragment(R.layout.fragment_x_ray_scan) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showCam()
        tilFragXRayMLReportBox.visibility = View.GONE
        iv_frag_x_ray_cam_icon.setOnClickListener{ getImageFromGallery() }
        iv_frag_x_ray_delete_image.setOnClickListener { deletePickedImage() }
        btFragXRaySubmitImage.setOnClickListener{
            getMLReport()
            uploadXRayReport()
        }

    }

    fun getMLReport(){
        tilFragXRayMLReportBox.visibility = View.VISIBLE
        tvFragXRayMLReportOfCTScan.setText("Processing...")
        tvFragXRayMLReportOfCTScan.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null)
        /* get report from internet */
        tvFragXRayMLReportOfCTScan.setText("All OK!")
        tvFragXRayMLReportOfCTScan.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check_green,0)

    }

    fun uploadXRayReport(){}

    private fun getImageFromGallery(){
        val i = Intent()
         i.apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(i,"Select any image"),200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 200 && resultCode == RESULT_OK && data!=null){
            val uri = data.data
            iv_frag_x_ray_uploaded_image.setImageURI(uri)
            hideCam()
        }
    }

    private fun deletePickedImage(){
        showCam()
    }

    private fun showCam(){
        iv_frag_x_ray_cam_icon.visibility = View.VISIBLE
        iv_frag_x_ray_uploaded_image.visibility = View.GONE
        iv_frag_x_ray_delete_image.visibility = View.GONE
    }

    private fun hideCam(){
        iv_frag_x_ray_cam_icon.visibility = View.GONE
        iv_frag_x_ray_uploaded_image.visibility = View.VISIBLE
        iv_frag_x_ray_delete_image.visibility = View.VISIBLE
    }

}