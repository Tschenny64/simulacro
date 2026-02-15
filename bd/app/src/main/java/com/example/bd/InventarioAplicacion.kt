package com.example.bd

import android.app.Application
import com.example.bd.datos.ContenedorApp
import com.example.bd.datos.ProductoContenedorApp

class InventarioAplicacion : Application() {
    lateinit var contenedor: ContenedorApp
    override fun onCreate() {
        super.onCreate()
        contenedor = ProductoContenedorApp(this)
    }
}