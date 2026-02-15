package com.example.retrofit.conexion

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import com.example.retrofit.modelo.Respuesta

//private const val BASE_URL = "https://swapi.dev/api/"
//
//private val json = Json { ignoreUnknownKeys = true }
//
//
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
//    .baseUrl(BASE_URL)
//    .build()
interface ServicioApi {
    @GET("people")
    suspend fun obtenerPersonaje(): Respuesta
}

//object Api {
//    val servicioRetrofit: ServicioApi by lazy {
//        retrofit.create(ServicioApi::class.java)
//    }
// }






