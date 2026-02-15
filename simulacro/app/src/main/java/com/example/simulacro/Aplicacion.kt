package com.example.simulacro

import android.app.Application
import com.example.simulacro.datos.ContenedorApp
import com.example.simulacro.datos.UsuarioContenedorApp

class Aplicacion : Application() {
    lateinit var contenedor: ContenedorApp
    override fun onCreate() {
        super.onCreate()
        contenedor = UsuarioContenedorApp(this)
    }
}