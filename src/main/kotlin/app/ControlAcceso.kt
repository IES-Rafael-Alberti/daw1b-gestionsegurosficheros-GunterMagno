package app

import data.ICargarUsuariosIniciales
import model.Perfil
import service.IServUsuarios
import ui.IEntradaSalida
import utils.IUtilFicheros

class ControlAcceso(
    private val rutaArchivoUsuarios: String,
    private val consola: IEntradaSalida,
    private val servUsuarios: IServUsuarios,
    private val fich: IUtilFicheros
) {
    fun verificarAcceso(): Perfil? {
        if (!fich.existeFichero(rutaArchivoUsuarios)) {
            consola.mostrar("No se encontró el archivo de usuarios. Se creará uno nuevo.")
            return crearUsuarioAdmin()
        }

        if (servUsuarios is ICargarUsuariosIniciales) {
            val cargadoUsuarios = servUsuarios.cargarUsuarios()

            if (!cargadoUsuarios) {
                consola.mostrarError("Error al cargar los usuarios existentes.")
                return null
            }
        }

        return iniciarSesion()
    }

    private fun crearUsuarioAdmin(): Perfil? {
        consola.mostrar("\nNo hay usuarios registrados. Debe crear un usuario administrador.")
        val nombre = consola.pedirInfo("Nombre de usuario: ","Error al crear el nombre de usuario.") { it.isNotBlank() }
        //ToDo deberia hacer para que no se repitan los nombres?
        val clave = consola.pedirInfoOculta("Contraseña: ")

        val usuarioCreado = servUsuarios.agregarUsuario(nombre, clave, Perfil.ADMIN)

        return if (usuarioCreado) {
            consola.mostrar("\nUsuario administrador creado con éxito.")
            Perfil.ADMIN
        } else {
            consola.mostrarError("Error al crear el usuario administrador.")
            null
        }
    }

    private fun iniciarSesion(): Perfil? {
        consola.mostrar("\n----- Inicio de Sesión ------")
        val nombre = consola.pedirInfo("Usuario: ")
        val clave = consola.pedirInfoOculta("Contraseña: ")

        val sesionIniciada = servUsuarios.iniciarSesion(nombre, clave)

        if (sesionIniciada == null) {
            consola.mostrarError("Usuario o contraseña incorrectos, intentelo de nuevo.")
            return null
        }else {
            consola.mostrar("\nSesión iniciada correctamente, $nombre")
            return sesionIniciada
        }
    }
}