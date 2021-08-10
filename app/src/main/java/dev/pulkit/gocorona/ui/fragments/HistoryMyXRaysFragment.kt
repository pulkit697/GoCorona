package dev.pulkit.gocorona.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import dev.pulkit.gocorona.R
import dev.pulkit.gocorona.adapters.CTScanHistoryFragmentAdapter
import dev.pulkit.gocorona.api.ctscan.CtscanClient.api
import kotlinx.android.synthetic.main.fragment_history_my_x_rays.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryMyXRaysFragment : Fragment(R.layout.fragment_history_my_x_rays) {

    private val auth by lazy { FirebaseAuth.getInstance().currentUser }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO){api.getMyCTScans(auth.uid)}
            if(response.isSuccessful)
            {
                val result = response.body()?: arrayListOf()
                rvHistoryFrag.apply {
                    adapter = CTScanHistoryFragmentAdapter(result)
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }
    }

}