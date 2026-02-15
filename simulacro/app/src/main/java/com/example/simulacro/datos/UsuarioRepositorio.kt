package com.example.simulacro.datos

import com.example.simulacro.conexion.ServicioApi
import com.example.simulacro.modelo.Mensaje
import com.example.simulacro.modelo.Usuario

interface UsuarioRepositorio {

    suspend fun obtenerUsuarios(): List<Usuario>
    suspend fun insertarUsuario(usuario: Usuario): Usuario
    suspend fun actualizarUsuario(id: String, usuario: Usuario): Usuario

    suspend fun enviarMensaje(id: String, mensaje: Mensaje): Usuario

    suspend fun eliminarUsuario(id: String): Usuario
}

class ConexionUsuarioRepositorio(
    private val ServicioApi: ServicioApi
) : UsuarioRepositorio {
    override suspend fun obtenerUsuarios(): List<Usuario> = ServicioApi.obtenerUsuarios()
    override suspend fun insertarUsuario(usuario: Usuario): Usuario = ServicioApi.insertarUsuario(usuario)
    override suspend fun actualizarUsuario(id: String, usuario: Usuario): Usuario = ServicioApi.actualizarUsuario(id, usuario)
    override suspend fun eliminarUsuario(id: String): Usuario = ServicioApi.eliminarUsuario(id)

    override suspend fun enviarMensaje(id: String, mensaje: Mensaje): Usuario {
        val usuario = ServicioApi.obtenerUsuarios().first() { it.id == id}
        val actualizado = usuario.copy(mensajes = usuario.mensajes + mensaje)
        return ServicioApi.actualizarUsuario(id, actualizado)
    }
}
