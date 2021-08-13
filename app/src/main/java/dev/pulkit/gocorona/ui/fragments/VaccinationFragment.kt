package dev.pulkit.gocorona.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dev.pulkit.gocorona.R
import dev.pulkit.gocorona.adapters.VaccinationAdapter
import dev.pulkit.gocorona.api.vaccineTracker.VaccineClient.api
import dev.pulkit.gocorona.models.vaccineTracker.VaccStDataItem
import kotlinx.android.synthetic.main.fragment_vaccination.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VaccinationFragment : Fragment(R.layout.fragment_vaccination) {
    val listOfStates = arrayListOf<VaccStDataItem>()
    val adapter = VaccinationAdapter(listOfStates)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvVaccineCount.visibility = View.GONE
        tvVaccineCountIndiaHeading.visibility = View.GONE
        tvDose1Heading.visibility = View.GONE
        tvDose2Heading.visibility = View.GONE
        tvIndiaDose1Cases.visibility = View.GONE
        tvIndiaDose2Cases.visibility = View.GONE
        tvVaccineLoading.visibility = View.VISIBLE
        rvVaccineCount.apply {
            adapter = this@VaccinationFragment.adapter
            layoutManager = LinearLayoutManager(this@VaccinationFragment.requireContext())
        }
        fetchData()
    }
    private fun fetchData() {
        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO){api.getStatesData()}
            if(response.isSuccessful) {
                rvVaccineCount.visibility = View.VISIBLE
                tvVaccineCountIndiaHeading.visibility = View.VISIBLE
                tvDose1Heading.visibility = View.VISIBLE
                tvDose2Heading.visibility = View.VISIBLE
                tvIndiaDose1Cases.visibility = View.VISIBLE
                tvIndiaDose2Cases.visibility = View.VISIBLE
                tvVaccineLoading.visibility = View.GONE
                response.body()?.vaccStData.let {
                    listOfStates.clear()
                    listOfStates.addAll(it!!)
                    adapter.notifyDataSetChanged()
                }
                response.body().let {
                    tvIndiaDose1Cases.text = it!!.indiaDose1.toString()
                    tvIndiaDose2Cases.text = it.indiaDose2.toString()
                }
            }
        }
    }
}