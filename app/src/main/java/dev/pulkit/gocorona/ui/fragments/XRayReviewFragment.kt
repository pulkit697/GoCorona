package dev.pulkit.gocorona.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dev.pulkit.gocorona.R
import dev.pulkit.gocorona.adapters.ReviewCTScansAdapter
import dev.pulkit.gocorona.api.ctscan.CtscanClient.api
import dev.pulkit.gocorona.models.ctscan.CTScanPost
import kotlinx.android.synthetic.main.fragment_x_ray_review.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class XRayReviewFragment : Fragment(R.layout.fragment_x_ray_review) {

    lateinit var result: ArrayList<CTScanPost>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO){ api.getAllCTScans()}
            if(response.isSuccessful) {
                result = response.body()?: arrayListOf()
                rvReviewFrag.apply {
                    adapter = ReviewCTScansAdapter(result)
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }
    }
}