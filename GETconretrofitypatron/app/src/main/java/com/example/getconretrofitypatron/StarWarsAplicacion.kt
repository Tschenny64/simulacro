package com.example.getconretrofitypatron

import android.app.Application
import com.example.getconretrofitypatron.datos.ContenedorApp
import com.example.getconretrofitypatron.datos.StarWarsContenedorApp

class StarWarsAplicacion : Application() {
    lateinit var contenedor: ContenedorApp
    override fun onCreate() {
        super.onCreate()
        contenedor = StarWarsContenedorApp()
    }
}