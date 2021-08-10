package dev.pulkit.gocorona.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.pulkit.gocorona.R
import dev.pulkit.gocorona.models.ctscan.CTScanPost
import kotlinx.android.synthetic.main.list_item_view_review_scan.view.*

class ReviewCTScansAdapter(private val list:List<CTScanPost>):RecyclerView.Adapter<ReviewCTScansAdapter.ReviewViewHolder>() {
    class ReviewViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        fun onBind(post:CTScanPost){
            Glide.with(itemView).load(post.imageUrl).error(R.drawable.loading_image).into(itemView.ivCTScanPostReviewFrag)
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