package com.example.crud

import android.app.Application
import com.example.crud.datos.ContenedorApp
import com.example.crud.datos.ContenedorAppp

class Aplicacion : Application() {
    lateinit var contenedor: ContenedorAppp
    override fun onCreate() {
        super.onCreate()
        contenedor = ContenedorApp()
    }
}