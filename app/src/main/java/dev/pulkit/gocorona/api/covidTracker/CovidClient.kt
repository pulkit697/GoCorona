package dev.pulkit.gocorona.api.covidTracker

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CovidClient {
    private var gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
    private var retrofit = Retrofit.Builder().baseUrl("https://api.covid19india.org/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    var api = retrofit.create(CovidDao::class.java)
}