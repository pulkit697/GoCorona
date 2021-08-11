package dev.pulkit.gocorona.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dev.pulkit.gocorona.R
import dev.pulkit.gocorona.adapters.CovidCountAdapter
import dev.pulkit.gocorona.api.covidTracker.CovidClient
import dev.pulkit.gocorona.models.covidTracker.StatewiseItem
import kotlinx.android.synthetic.main.fragment_covid_track.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CovidTrackFragment : Fragment(R.layout.fragment_covid_track) {
    var listOfStates:ArrayList<StatewiseItem> = arrayListOf()
    var adapter = CovidCountAdapter(listOfStates)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCovidCount.apply {
            adapter = this@CovidTrackFragment.adapter
            layoutManager = LinearLayoutManager(this@CovidTrackFragment.requireContext())
        }
        fetchData()
    }
    fun fetchData()
    {
        GlobalScope.launch(Dispatchers.Main)
        {
            val response = withContext(Dispatchers.IO){CovidClient.api.getAllStatesData()}
            if(response.isSuccessful)
            {
                response.body()?.statewise?.let {
                    listOfStates.clear()
                    listOfStates.addAll(it)
                    listOfStates.removeAt(0)
                    adapter.notifyDataSetChanged()
                    tvIndiaActiveCases.text = it.get(0).active
                    tvIndiaConfirmedCases.text = it.get(0).confirmed
                    tvIndiaRecoveredCases.text = it.get(0).recovered
                    tvIndiaDeceasedCases.text = it.get(0).deaths
                }
            }
        }
    }
}