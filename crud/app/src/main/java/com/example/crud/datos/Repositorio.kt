    package com.example.crud.datos

import com.example.crud.conexion.ServicioApi
import com.example.crud.modelo.Trabajador

interface Repositorio {
    suspend fun obtenerTrabajadores(): List<Trabajador>
    suspend fun insertarTrabajador(trabajador: Trabajador): Trabajador
    suspend fun actualizarTrabajador(id: String, trabajador: Trabajador): Trabajador
    suspend fun eliminarTrabajador(id: String): Trabajador
}

class ConexionTrabajadorRepositorio(
    private val ServicioApi: ServicioApi
) : Repositorio {
    override suspend fun obtenerTrabajadores(): List<Trabajador> = ServicioApi.obtenerTrabajadores()
    override suspend fun insertarTrabajador(trabajador: Trabajador): Trabajador = ServicioApi.insertarTrabajador(trabajador)
    override suspend fun actualizarTrabajador(id: String, trabajador: Trabajador): Trabajador = ServicioApi.actualizarTrabajador(id, trabajador)
    override suspend fun eliminarTrabajador(id: String): Trabajador = ServicioApi.eliminarTrabajador(id)

}