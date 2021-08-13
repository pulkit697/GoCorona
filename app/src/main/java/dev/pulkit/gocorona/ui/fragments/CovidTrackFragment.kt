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
import kotlinx.android.synthetic.main.fragment_vaccination.*
import kotlinx.android.synthetic.main.list_item_covid_state_count.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CovidTrackFragment : Fragment(R.layout.fragment_covid_track) {
    var listOfStates:ArrayList<StatewiseItem> = arrayListOf()
    var adapter = CovidCountAdapter(listOfStates)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCovidCount.visibility = View.GONE
        tvActiveHeading.visibility = View.GONE
        tvRecoveredHeading.visibility = View.GONE
        tvConfirmedHeading.visibility = View.GONE
        tvDeceasedHeading.visibility = View.GONE
        tvCovidCountIndiaHeading.visibility = View.GONE
        tvIndiaConfirmedCases.visibility = View.GONE
        tvIndiaActiveCases.visibility = View.GONE
        tvIndiaRecoveredCases.visibility = View.GONE
        tvIndiaDeceasedCases.visibility = View.GONE
        tvCovidLoading.visibility = View.VISIBLE
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
                rvCovidCount.visibility = View.VISIBLE
                tvActiveHeading.visibility = View.VISIBLE
                tvRecoveredHeading.visibility = View.VISIBLE
                tvConfirmedHeading.visibility = View.VISIBLE
                tvDeceasedHeading.visibility = View.VISIBLE
                tvCovidCountIndiaHeading.visibility = View.VISIBLE
                tvIndiaConfirmedCases.visibility = View.VISIBLE
                tvIndiaActiveCases.visibility = View.VISIBLE
                tvIndiaRecoveredCases.visibility = View.VISIBLE
                tvIndiaDeceasedCases.visibility = View.VISIBLE
                tvCovidLoading.visibility = View.GONE
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