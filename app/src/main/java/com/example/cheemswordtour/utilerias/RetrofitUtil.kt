package com.example.cheemswordtour.utilerias

import com.example.cheemswordtour.interfaces.CheemsWorldTourApi
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtil {
    fun getApi() : CheemsWorldTourApi? {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            // CAMBIAR URL!!!!
            .baseUrl("http://192.168.0.37:8000/api/")
            .addConverterFactory(GsonConverterFactory.create((gson)))
            .build()
        return retrofit.create(CheemsWorldTourApi::class.java)
    }



}