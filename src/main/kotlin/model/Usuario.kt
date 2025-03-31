package model

class Usuario(val nombre: String,val perfil: String): IExportable{

    var clave: String = ""
        private set

    fun cambiarClave(nuevaClaveEncriptada: String){
        clave = nuevaClaveEncriptada
    }

    override fun serializar(separador: String): String {
        return "$nombre$separador$perfil$separador$clave"
    }

    companion object{
        fun crearUsuario(datos: List<String>): Usuario?{
            try {
                val nombre = datos[0]
                val perfil = datos[1]

                return Usuario(nombre,perfil)

            }catch (e: IllegalArgumentException){
                println("Error al intentar crear el usuario")
                return null
            }
        }
    }
}