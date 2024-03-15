package com.example.petcareapp20.retrofit

import com.example.petcareapp20.BuildConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL= BuildConfig.BASE_URL
//const val API_KEY=""

interface DoctorInterface{
    @GET("vetinfo")
    fun getVetInfo(@Query("id") id:Int, @Query("country") country:String): Call<DoctorCallList>
}

object DoctorDetailService{
    val doctorInstance:DoctorInterface

    init {
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        doctorInstance=retrofit.create(DoctorInterface::class.java)
    }
}