package dev.pulkit.gocorona.models.vaccineTracker

import com.google.gson.annotations.SerializedName

data class VaccStDataItem(

	@field:SerializedName("dose2")
	val dose2: String? = null,

	@field:SerializedName("dose1")
	val dose1: String? = null,

	@field:SerializedName("st_name")
	val stName: String? = null,

	@field:SerializedName("covid_state_name")
	val covidStateName: String? = null,

	@field:SerializedName("covid_state_id")
	val covidStateId: String? = null,

	@field:SerializedName("last_dose2")
	val lastDose2: String? = null,

	@field:SerializedName("state_id")
	val stateId: String? = null,

	@field:SerializedName("total_doses")
	val totalDoses: String? = null,

	@field:SerializedName("last_total_doses")
	val lastTotalDoses: String? = null,

	@field:SerializedName("last_dose1")
	val lastDose1: String? = null
)