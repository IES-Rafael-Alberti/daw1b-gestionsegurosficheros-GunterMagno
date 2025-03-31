package service

import data.IRepoUsuarios
import model.Perfil
import model.Usuario
import utils.IUtilSeguridad

class GestorUsuarios(private val repoUsuarios: IRepoUsuarios, private val utilSeguridad: IUtilSeguridad): IServUsuarios{

    override fun iniciarSesion(nombre: String, clave: String): Perfil? {
        val usuario = repoUsuarios.buscar(nombre)
        if (usuario != null){
            return if (utilSeguridad.verificarClave(clave,)) usuario.perfil else null//ToDo de donde sale el hashAlmacenado
        }
        return null
    }

    override fun agregarUsuario(nombre: String, clave: String, perfil: Perfil): Boolean {

        val claveEncriptada = utilSeguridad.encriptarClave(clave)
        val usuario = Usuario(nombre,claveEncriptada,perfil)
        return repoUsuarios.agregar(usuario)

    }

    override fun eliminarUsuario(nombre: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun cambiarClave(usuario: Usuario, nuevaClave: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun buscarUsuario(nombre: String): Usuario? {
        return repoUsuarios.buscar(nombre)
    }

    override fun consultarTodos(): List<Usuario> {
        TODO("Not yet implemented")
    }

    override fun consultarPorPerfil(perfil: Perfil): List<Usuario> {
        TODO("Not yet implemented")
    }
}