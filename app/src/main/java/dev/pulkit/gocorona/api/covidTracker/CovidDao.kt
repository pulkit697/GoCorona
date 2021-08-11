package dev.pulkit.gocorona.api.covidTracker

import dev.pulkit.gocorona.models.covidTracker.CovidResponse
import retrofit2.Response
import retrofit2.http.GET

interface CovidDao {
    @GET("data.json")
    suspend fun getAllStatesData(): Response<CovidResponse>
}