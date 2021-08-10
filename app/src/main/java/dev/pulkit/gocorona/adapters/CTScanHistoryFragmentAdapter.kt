package dev.pulkit.gocorona.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.pulkit.gocorona.R
import dev.pulkit.gocorona.models.ctscan.CTScanPost
import dev.pulkit.gocorona.models.ctscan.Comment
import dev.pulkit.gocorona.ui.MainActivity
import dev.pulkit.gocorona.ui.fragments.MyCTScanAllCommentsFragment
import kotlinx.android.synthetic.main.list_item_view_history_x_rays.view.*

class CTScanHistoryFragmentAdapter(private val list:ArrayList<CTScanPost>):RecyclerView.Adapter<CTScanHistoryFragmentAdapter.CTScanHistoryViewHolder>() {
    class CTScanHistoryViewHolder(val itemView: View):RecyclerView.ViewHolder(itemView) {
        fun onBind(ctScanPost: CTScanPost)
        {
            itemView.apply {
                tvFragListHistoryMlReportAnswer.text = ctScanPost.MlReport
                tvFragListHistoryTimeStampAnswer.text = ctScanPost.timeStamp
                if(ctScanPost.MlReport == "Covid Positive"){
                    ivFragListHistoryMLReport.setImageResource(R.drawable.ic_warn_red)
                }else{
                    ivFragListHistoryMLReport.setImageResource(R.drawable.ic_check_green)
                }
                tvFragHistoryOpenComments.setOnClickListener { goToComments(itemView.context,ctScanPost.comments) }
            }
        }

        private fun goToComments(context: Context, comments: ArrayList<Comment>) {
            val activity = context as MainActivity
            activity.navigateToComments(comments)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CTScanHistoryViewHolder {
        return CTScanHistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_view_history_x_rays,parent,false))
    }

    override fun onBindViewHolder(holder: CTScanHistoryViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}