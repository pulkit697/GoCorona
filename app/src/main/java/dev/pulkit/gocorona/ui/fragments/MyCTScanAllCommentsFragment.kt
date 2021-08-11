package dev.pulkit.gocorona.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dev.pulkit.gocorona.R
import dev.pulkit.gocorona.adapters.AllCommentsAdapter
import dev.pulkit.gocorona.models.ctscan.Comment
import kotlinx.android.synthetic.main.fragment_my_c_t_scan_all_comments.*

class MyCTScanAllCommentsFragment : Fragment(R.layout.fragment_my_c_t_scan_all_comments) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var comments = arrayListOf<Comment>()
        if(arguments!=null){
            Log.d("pulkit","arguments not null")
            comments = arguments?.getSerializable("commentsList") as ArrayList<Comment>
        }else{
            Log.d("pulkit","arguments null")
        }
        rvCommentsFrag.apply {
            Log.d("pulkit","" + comments.size)
            adapter = AllCommentsAdapter(comments)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}