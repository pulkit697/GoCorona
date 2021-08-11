package dev.pulkit.gocorona.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.pulkit.gocorona.R
import dev.pulkit.gocorona.models.covidTracker.StatewiseItem
import kotlinx.android.synthetic.main.list_item_covid_state_count.view.*

class CovidCountAdapter(private val listOfStates:List<StatewiseItem>) :RecyclerView.Adapter<CovidCountAdapter.CovidCountViewHolder>(){
    class CovidCountViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        fun onBind(state:StatewiseItem){
            itemView.apply {
                tvStateNameCovidRV.text = state.state
                tvActiveCountRV.text = state.active
                tvConfirmedCountRV.text = state.confirmed
                tvRecoveredCountRV.text = state.recovered
                tvDeceasedCountRV.text = state.deaths
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidCountViewHolder =
        CovidCountViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_covid_state_count,parent,false)
        )

    override fun onBindViewHolder(holder: CovidCountViewHolder, position: Int) {
        holder.onBind(listOfStates[position])
    }

    override fun getItemCount(): Int = listOfStates.size
}