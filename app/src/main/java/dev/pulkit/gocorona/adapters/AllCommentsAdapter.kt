package dev.pulkit.gocorona.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.pulkit.gocorona.R
import dev.pulkit.gocorona.models.ctscan.Comment
import kotlinx.android.synthetic.main.fragment_my_c_t_scan_all_comments.view.*
import kotlinx.android.synthetic.main.list_item_comment.view.*

class AllCommentsAdapter(private val list:ArrayList<Comment>):RecyclerView.Adapter<AllCommentsAdapter.CommentViewHolder>() {
    class CommentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun onBind(comment: Comment)
        {
            itemView.tvCommentListItem.text = comment.caption
            itemView.tvCommentListItemResult.text = "Result: " + comment.result
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_comment,parent,false))
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int  = list.size
}