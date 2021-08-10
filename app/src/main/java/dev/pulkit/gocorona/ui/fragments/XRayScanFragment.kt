package dev.pulkit.gocorona.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import dev.pulkit.gocorona.R
import dev.pulkit.gocorona.api.ctscan.CtscanClient.api
import dev.pulkit.gocorona.ml.TfLiteModel
import kotlinx.android.synthetic.main.fragment_x_ray_scan.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.sql.Timestamp
import java.util.regex.Matcher
import java.util.regex.Pattern

class XRayScanFragment : Fragment(R.layout.fragment_x_ray_scan) {

    lateinit var img:Bitmap
    lateinit var uri:Uri
    lateinit var mlResult:String

    val auth by lazy {
        FirebaseAuth.getInstance().currentUser
    }

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

    private fun getMLReport() {
        tilFragXRayMLReportBox.visibility = View.VISIBLE
        tvFragXRayMLReportOfCTScan.setText("Processing...")
        tvFragXRayMLReportOfCTScan.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null)
        /* get report from ml model */
        try {
            val model = TfLiteModel.newInstance(requireContext())
            img = Bitmap.createScaledBitmap(img,224,224,true)
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(img)
            val byteBuffer = tensorImage.buffer

            inputFeature0.loadBuffer(byteBuffer)
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            model.close()
            Log.d("pulkit","" + outputFeature0.floatArray.contentToString())
            val nonCovid = outputFeature0.floatArray[1]
            val covid = outputFeature0.floatArray[0]
            if(covid>nonCovid){
                mlResult = "Covid Positive"
                tvFragXRayMLReportOfCTScan.setText(mlResult)
                tvFragXRayMLReportOfCTScan.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_warn_red,0)
            }else{
                mlResult = "All Okay! You are Safe"
                tvFragXRayMLReportOfCTScan.setText(mlResult)
                tvFragXRayMLReportOfCTScan.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check_green,0)
            }
        }
        catch (err:Error)
        {
            tvFragXRayMLReportOfCTScan.setText("" + err.message)
        }

    }

    private fun uploadXRayReport(){
        val file = getFile(uri)
        Log.d("pulkit","hello... " + file.extension)
        val requestFile = RequestBody.create(MediaType.parse("image/${file.extension}"),file)
        val fileBody = MultipartBody.Part.createFormData("files",file.name,requestFile)
        val userId = RequestBody.create(MediaType.parse("multipart/form-data"),auth.uid)
        val mlReport = RequestBody.create(MediaType.parse("multipart/form-data"),mlResult.toString())
        val timeStamp = RequestBody.create(MediaType.parse("multipart/form-data"),
            Timestamp(System.currentTimeMillis()).toString())
        GlobalScope.launch(Dispatchers.Main)
        {
            val response = withContext(Dispatchers.IO){api.postMyCTScan(userId,mlReport,timeStamp,fileBody)}
            if(response.isSuccessful)
            {
                val result = response.body()!!.message
                Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
                Log.d("pulkit",result)
            }
            else
            {
                Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()
                Log.d("pulkit",response.toString())
            }
        }
    }

    private fun getImageFromGallery(){
        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i,200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 200 && resultCode == RESULT_OK && data!=null){
            val uri = data.data
            if(uri!=null) {
                iv_frag_x_ray_uploaded_image.setImageURI(uri)
                hideCam()
                btFragXRaySubmitImage.isEnabled = true
                img = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                this.uri = uri
            }
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

    private fun getFile(uri:Uri):File {
        val context = requireContext()
        val ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(uri))
        val f = File(context.cacheDir, "auth.$ext")
        f.createNewFile()
        val bmp = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        val bos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG,0,bos)
        val bitmapData = bos.toByteArray()

        val fos = FileOutputStream(f)
        fos.write(bitmapData)
        fos.flush()
        fos.close()
        return f
    }

}