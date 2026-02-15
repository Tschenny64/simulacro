package com.example.simulacro.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Usuarios")
data class UsuarioBD(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val telefono: String
)
