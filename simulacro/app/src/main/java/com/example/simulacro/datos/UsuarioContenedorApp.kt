package com.example.simulacro.datos

import android.content.Context
import com.example.simulacro.conexion.BaseDatos
import com.example.simulacro.conexion.ServicioApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import kotlin.getValue

interface ContenedorApp {

    val UsuarioRepositorio: UsuarioRepositorio
    val usuarioRepositorioBD: UsuarioRepositorioBD
}


class UsuarioContenedorApp(private val context: Context) : ContenedorApp {

    private val baseUrl = "http://10.0.2.2:3000/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val servicioRetrofit: ServicioApi by lazy {
        retrofit.create(ServicioApi::class.java)
    }

    //DECLARACION DE REPOSITORIOS

    override val UsuarioRepositorio: UsuarioRepositorio by lazy {
        ConexionUsuarioRepositorio(servicioRetrofit)
    }

    override val usuarioRepositorioBD: UsuarioRepositorioBD by lazy {
        ConexionUsuarioRepositorioBD(BaseDatos.obtenerBaseDatos(context).usuarioDao())
    }
}