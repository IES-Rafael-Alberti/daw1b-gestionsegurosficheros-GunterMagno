package service

import model.Perfil
import model.Usuario

class GestorSeguros: IServUsuarios {
    override fun iniciarSesion(nombre: String, clave: String): Perfil? {
        TODO("Not yet implemented")
    }

    override fun agregarUsuario(nombre: String, clave: String, perfil: Perfil): Boolean {
        TODO("Not yet implemented")
    }

    override fun eliminarUsuario(nombre: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun cambiarClave(usuario: Usuario, nuevaClave: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun buscarUsuario(nombre: String): Usuario? {
        TODO("Not yet implemented")
    }

    override fun consultarTodos(): List<Usuario> {
        TODO("Not yet implemented")
    }

    override fun consultarPorPerfil(perfil: Perfil): List<Usuario> {
        TODO("Not yet implemented")
    }
}