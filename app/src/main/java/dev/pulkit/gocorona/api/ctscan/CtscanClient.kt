package dev.pulkit.gocorona.api.ctscan

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// https://rocky-lake-08141.herokuapp.com/

object CtscanClient {
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://rocky-lake-08141.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var api = retrofit.create(CtScanDao::class.java)
}