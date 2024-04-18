package com.example.petcareapp20.retrofit_sec

import com.example.petcareapp20.BuildConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL= BuildConfig.BASE_URL
//const val API_KEY=""

interface DrugInterface{
    @GET("druginfo")
    fun getDrugInfo(@Query("id") id:Int, @Query("country") country:String): Call<DrugCallList>
}

object DrugDetailService{
    val drugInstance: DrugInterface

    init {
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        drugInstance=retrofit.create(DrugInterface::class.java)
    }
}