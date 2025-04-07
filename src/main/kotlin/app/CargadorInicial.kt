package app

import data.ICargarSegurosIniciales
import data.ICargarUsuariosIniciales
import model.Seguro
import ui.IEntradaSalida

class CargadorInicial(private val consola: IEntradaSalida) {


    fun cargarInfo(repoUsuarios: ICargarUsuariosIniciales,repoSeguros: ICargarSegurosIniciales,mapaSeguros: Map<String, (List<String>) -> Seguro>): Boolean {

        val usuariosCargados = cargarUsuarios(repoUsuarios)
        val segurosCargados = cargarSeguros(repoSeguros, mapaSeguros)

        return if (usuariosCargados && segurosCargados) {
            consola.mostrar("Información inicial cargada correctamente.")
            true
        }else {
            consola.mostrarError("Error al cargar datos iniciales.")
            false
        }
    }

    private fun cargarUsuarios(repoUsuarios: ICargarUsuariosIniciales): Boolean {

        return try {
            if(repoUsuarios.cargarUsuarios()){
                consola.mostrar("Usuarios cargados correctamente.")
                true
            }
            else{
                throw Exception("No se pudieron cargar los usuarios.") //ToDo esto funciona bien?
            }
        }catch (e:Exception){//ToDo que excepcion se captura aqui?
            consola.mostrarError("No se pudieron cargar los usuarios.")
            false
        }
    }

    private fun cargarSeguros(repoSeguros: ICargarSegurosIniciales,mapaSeguros: Map<String, (List<String>) -> Seguro>): Boolean {

        return try {
            if(repoSeguros.cargarSeguros(mapaSeguros)){
                consola.mostrar("Seguros cargados correctamente.")
                true
            }else {
                throw Exception("No se pudieron cargar los seguros.") //ToDo esto funciona bien?
            }
        }catch (e:Exception){//ToDo que excepcion se captura aqui?
            consola.mostrarError("No se pudieron cargar los seguros")
            false
        }
    }
}