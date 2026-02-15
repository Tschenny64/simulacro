package com.example.retrofit

import android.app.Application
import com.example.retrofit.datos.ContenedorApp
import com.example.retrofit.datos.StarWarsContenedorApp

class StarWarsAplicacion : Application() {
    lateinit var contenedor: ContenedorApp
    override fun onCreate() {
        super.onCreate()
        contenedor = StarWarsContenedorApp()
    }
}