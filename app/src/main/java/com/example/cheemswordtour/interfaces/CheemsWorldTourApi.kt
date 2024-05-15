package com.example.cheemswordtour.interfaces

import com.example.cheemswordtour.entities.Registro
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CheemsWorldTourApi {

    @GET("obtener_todos")
    fun getLocations(): Call<List<Registro>>

    @GET("obtener_registro/{id}")
    fun getLocation(@Path("id") id: Int): Call<Registro>

    @POST("agregar_registro")
    fun addLocation(@Body registro: Registro): Call<Registro>





}