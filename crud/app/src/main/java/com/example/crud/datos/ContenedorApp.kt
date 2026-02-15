package com.example.crud.datos

import com.example.crud.conexion.ServicioApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

interface ContenedorAppp {
    val Repositorio: Repositorio
}

class ContenedorApp : ContenedorAppp {
    private val baseUrl = "http://10.0.2.2:3000/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val servicioRetrofit: ServicioApi by lazy {
        retrofit.create(ServicioApi::class.java)
    }
    override val Repositorio: Repositorio by lazy {
        ConexionTrabajadorRepositorio(servicioRetrofit)
    }
}