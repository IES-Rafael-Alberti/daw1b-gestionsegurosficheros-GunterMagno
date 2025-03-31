package model

class Usuario(val nombre: String,clave: String,val perfil: Perfil): IExportable{

    var clave: String = clave
        private set

    fun cambiarClave(nuevaClaveEncriptada: String){
        clave = nuevaClaveEncriptada
    }

    override fun serializar(separador: String): String {
        return "$nombre$separador$perfil$separador$clave"
    }

    override fun toString(): String {
        return "Usuario (nombre= $nombre, clave= $clave, perfil= $perfil)"
    }

    companion object{
        fun crearUsuario(datos: List<String>): Usuario?{
            try {
                val nombre = datos[0]
                val clave = datos[1]
                val perfil = Perfil.getPerfil(datos[2])

                return Usuario(nombre,clave,perfil)

            }catch (e: IllegalArgumentException){
                println("Error al intentar crear el usuario")
                return null
            }
        }
    }
}