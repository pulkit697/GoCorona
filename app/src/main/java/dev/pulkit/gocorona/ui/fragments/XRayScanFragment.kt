package dev.pulkit.gocorona.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.Fragment
import dev.pulkit.gocorona.R
import dev.pulkit.gocorona.ml.TfLiteModel
import kotlinx.android.synthetic.main.fragment_x_ray_scan.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer

class XRayScanFragment : Fragment(R.layout.fragment_x_ray_scan) {

    lateinit var img:Bitmap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showCam()
        tilFragXRayMLReportBox.visibility = View.GONE
        btFragXRaySubmitImage.isEnabled = false
        iv_frag_x_ray_cam_icon.setOnClickListener{ getImageFromGallery() }
        iv_frag_x_ray_delete_image.setOnClickListener { deletePickedImage() }
        btFragXRaySubmitImage.setOnClickListener{
            getMLReport()
            uploadXRayReport()
        }
    }

    fun getMLReport() {
        tilFragXRayMLReportBox.visibility = View.VISIBLE
        tvFragXRayMLReportOfCTScan.setText("Processing...")
        tvFragXRayMLReportOfCTScan.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null)
        /* get report from ml model */
        try {
            val model = TfLiteModel.newInstance(requireContext())
            img = Bitmap.createScaledBitmap(img,1,1,true)
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 1, 1, 3), DataType.FLOAT32)
            val tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(img)
            val byteBuffer = tensorImage.buffer

            inputFeature0.loadBuffer(byteBuffer)
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            model.close()

            tvFragXRayMLReportOfCTScan.setText(" Result: " + outputFeature0.floatArray[0])
        }
        catch (err:Error)
        {
            tvFragXRayMLReportOfCTScan.setText("" + err.message)
        }
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
            btFragXRaySubmitImage.isEnabled = true
            img = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,uri)
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