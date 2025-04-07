package data

import model.Usuario
import utils.IUtilFicheros

class RepoUsuariosFich(
    private val rutaArchivo: String,
    private val utilFicheros: IUtilFicheros
) : RepoUsuariosMem(), ICargarUsuariosIniciales, IRepoUsuarios {

    override fun agregar(usuario: Usuario): Boolean {
        if (buscar(usuario.nombre) != null) return false
        if (super.agregar(usuario) && utilFicheros.agregarLinea(rutaArchivo, usuario.serializar())) {
            return true
        }
        return false
    }

    override fun eliminar(usuario: Usuario): Boolean {
        val usuarioEliminado = obtenerTodos().filter { it != usuario }
        if (utilFicheros.escribirArchivo(rutaArchivo, usuarioEliminado)) {
            return super.eliminar(usuario)
        }
        return false
    }

    override fun cambiarClave(usuario: Usuario, nuevaClave: String): Boolean {
        usuario.cambiarClave(nuevaClave)
        val usuarios = obtenerTodos()
        return utilFicheros.escribirArchivo(rutaArchivo, usuarios)
    }

    override fun cargarUsuarios(): Boolean {
        val lineas = utilFicheros.leerArchivo(rutaArchivo)

        if (lineas.isNotEmpty()) {
            lineas.forEach { linea ->
                if (linea.isNotBlank()) {

                    val datos = linea.split(";")
                    if (datos.size == 3) {
                        Usuario.crearUsuario(datos)?.let { usuarios.add(it) }

                        //usuarios.add(Usuario.crearUsuario(datos))
                    }
                }
            }
            return true
        }
        return false
    }
}