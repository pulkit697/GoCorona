package dev.pulkit.gocorona.models.vaccineTracker

import com.google.gson.annotations.SerializedName

data class VaccinationResponse(

	@field:SerializedName("india_total_doses")
	val indiaTotalDoses: Int? = null,

	@field:SerializedName("india_last_dose2")
	val indiaLastDose2: Int? = null,

	@field:SerializedName("vacc_st_data")
	val vaccStData: List<VaccStDataItem>? = null,

	@field:SerializedName("india_dose1")
	val indiaDose1: Int? = null,

	@field:SerializedName("india_last_dose1")
	val indiaLastDose1: Int? = null,

	@field:SerializedName("india_last_total_doses")
	val indiaLastTotalDoses: Int? = null,

	@field:SerializedName("day")
	val day: String? = null,

	@field:SerializedName("india_dose2")
	val indiaDose2: Int? = null
)