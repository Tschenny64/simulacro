package com.example.simulacro.datos

import com.example.simulacro.dao.UsuarioDao
import com.example.simulacro.modelo.UsuarioBD

interface UsuarioRepositorioBD {
    suspend fun obtenerPorIdBD(id: Int): UsuarioBD
    suspend fun obtenerTodosBD(): List<UsuarioBD>
    suspend fun insertarBD(usuarioBD: UsuarioBD)
    suspend fun actualizarBD(usuarioBD: UsuarioBD)
    suspend fun eliminarBD(usuarioBD: UsuarioBD)
}

class ConexionUsuarioRepositorioBD(
    private val dao: UsuarioDao
) : UsuarioRepositorioBD {
    override suspend fun obtenerPorIdBD(id: Int): UsuarioBD = dao.obtenerPorIdBD(id)
    override suspend fun obtenerTodosBD(): List<UsuarioBD> = dao.obtenerTodosUsuariosBD()
    override suspend fun insertarBD(usuarioBD: UsuarioBD) = dao.insertarBD(usuarioBD)
    override suspend fun actualizarBD(usuarioBD: UsuarioBD) = dao.actualizarBD(usuarioBD)
    override suspend fun eliminarBD(usuarioBD: UsuarioBD) = dao.eliminarBD(usuarioBD)
}
