package dev.pulkit.gocorona.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.pulkit.gocorona.*
import dev.pulkit.gocorona.api.ctscan.CtscanClient.api
import dev.pulkit.gocorona.models.ctscan.CTScanPost
import kotlinx.android.synthetic.main.fragment_my_c_t_scan_all_comments.view.*
import kotlinx.android.synthetic.main.list_item_view_review_scan.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody

class ReviewCTScansAdapter(private val list:List<CTScanPost>):RecyclerView.Adapter<ReviewCTScansAdapter.ReviewViewHolder>() {
    class ReviewViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        lateinit var result: String
        fun onBind(post:CTScanPost){
            Glide.with(itemView).load(post.imageUrl).error(R.drawable.sample_ct_scan).into(itemView.ivCTScanPostReviewFrag)
            result = "all okay"
            itemView.apply {
                btAllOkayReviewFrag.setOnClickListener {
                    result = (it as Button).text.toString()
                    setColors(it, COLOR_GREEN, COLOR_WHITE)
                    setColors(btShouldConsultReviewFrag, COLOR_WHITE, COLOR_BLACK)
                    setColors(btUrgentReviewFrag, COLOR_WHITE, COLOR_BLACK)
                }
                btShouldConsultReviewFrag.setOnClickListener {
                    result = (it as Button).text.toString()
                    setColors(it, COLOR_YELLOW, COLOR_WHITE)
                    setColors(btAllOkayReviewFrag, COLOR_WHITE, COLOR_BLACK)
                    setColors(btUrgentReviewFrag, COLOR_WHITE, COLOR_BLACK)
                }
                btUrgentReviewFrag.setOnClickListener {
                    result = (it as Button).text.toString()
                    setColors(it, COLOR_RED, COLOR_WHITE)
                    setColors(btShouldConsultReviewFrag, COLOR_WHITE, COLOR_BLACK)
                    setColors(btAllOkayReviewFrag, COLOR_WHITE, COLOR_BLACK)
                }
                btPostCommentReviewFrag.setOnClickListener { postComment(post,itemView.etCommentReviewFrag.text.toString()) }
            }
        }

        private fun postComment(ctScanPost: CTScanPost,comment:String) {
            val userId = RequestBody.create(MediaType.parse("multipart/form-data"),ctScanPost.userId)
            val timeStamp = RequestBody.create(MediaType.parse("multipart/form-data"),ctScanPost.timeStamp)
            val doctorName = RequestBody.create(MediaType.parse("multipart/form-data"),"random doctor")
            val caption = RequestBody.create(MediaType.parse("multipart/form-data"),comment)
            val resultRB = RequestBody.create(MediaType.parse("multipart/form-data"),result)
            GlobalScope.launch(Dispatchers.Main)
            {
                val response = withContext(Dispatchers.IO){api.postMyComment(userId,timeStamp,doctorName,caption,resultRB)}
                if(response.isSuccessful)
                {
                    val result1 = response.body()!!.message
                    Toast.makeText(itemView.context, result1, Toast.LENGTH_SHORT).show()
                    Log.d("pulkit",result1)
                }
            }

        }

        private fun setColors(itemView: Button, bgColor:Int, txtColor:Int) {
            itemView.setBackgroundColor(bgColor)
            itemView.setTextColor(txtColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view_review_scan,parent,false)
        return ReviewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}