package com.example.cheemswordtour.utilerias

import com.example.cheemswordtour.interfaces.CheemsWorldTourApi
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitUtil {



    fun getApi() : CheemsWorldTourApi? {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create((gson)))
            .build()
        return retrofit.create(CheemsWorldTourApi::class.java)
    }



}