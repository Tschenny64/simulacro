package com.example.simulacro.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import com.example.simulacro.modelo.UsuarioBD


@Dao
interface UsuarioDao {
    @Query("SELECT * from Usuarios WHERE id = :id")
    suspend fun obtenerPorIdBD(id: Int): UsuarioBD

    @Query("SELECT * from Usuarios ORDER BY nombre ASC")
    suspend fun obtenerTodosUsuariosBD(): List<UsuarioBD>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarBD(usuarioBD: UsuarioBD)

    @Update
    suspend fun actualizarBD(usuarioBD: UsuarioBD)

    @Delete
    suspend fun eliminarBD(usuarioBD: UsuarioBD)
}