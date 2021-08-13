package dev.pulkit.gocorona.api.vaccineTracker

import dev.pulkit.gocorona.models.vaccineTracker.VaccinationResponse
import retrofit2.Response
import retrofit2.http.GET

interface VaccineDao {

    @GET("sites/default/files/covid/vaccine/vaccine_counts_today.json")
    suspend fun getStatesData():Response<VaccinationResponse>

}