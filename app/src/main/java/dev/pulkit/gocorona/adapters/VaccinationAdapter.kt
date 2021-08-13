package dev.pulkit.gocorona.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.pulkit.gocorona.R
import dev.pulkit.gocorona.models.vaccineTracker.VaccStDataItem
import kotlinx.android.synthetic.main.list_item_vaccine_rv.view.*

class VaccinationAdapter(private val listOfStates:ArrayList<VaccStDataItem>):RecyclerView.Adapter<VaccinationAdapter.VaccinationViewHolder>() {
    class VaccinationViewHolder(val itemView:View):RecyclerView.ViewHolder(itemView) {
        fun onBind(state:VaccStDataItem){
            itemView.apply {
                tvStateNameVaccineRV.text = state.stName
                tvDose1CountRV.text = state.dose1
                tvDose2CountRV.text = state.dose2
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccinationViewHolder =
        VaccinationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_vaccine_rv,parent,false))

    override fun onBindViewHolder(holder: VaccinationViewHolder, position: Int) {
        holder.onBind(listOfStates[position])
    }

    override fun getItemCount(): Int = listOfStates.size
}