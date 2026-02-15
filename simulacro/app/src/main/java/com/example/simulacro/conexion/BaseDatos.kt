package com.example.simulacro.conexion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simulacro.dao.UsuarioDao

import com.example.simulacro.modelo.UsuarioBD


@Database(entities = [UsuarioBD::class], version = 1, exportSchema = false)
    abstract class BaseDatos : RoomDatabase() {
        abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var Instance: BaseDatos? = null

        fun obtenerBaseDatos(context: Context): BaseDatos {
            return Instance ?: synchronized(this) {                 //AQUI PONER EL NOMBRE DE LA BASE DE DATOS
                Room.databaseBuilder(context, BaseDatos::class.java, "basedatos")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}