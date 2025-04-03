package app

import model.Perfil
import service.IServUsuarios
import ui.IEntradaSalida
import utils.IUtilFicheros

/**
 * Clase responsable del control de acceso de usuarios: alta inicial, inicio de sesión
 * y recuperación del perfil. Su objetivo es asegurar que al menos exista un usuario
 * en el sistema antes de acceder a la aplicación.
 *
 * Esta clase encapsula toda la lógica relacionada con la autenticación de usuarios,
 * separando así la responsabilidad del acceso del resto de la lógica de negocio.
 *
 * Utiliza inyección de dependencias (DIP) para recibir los servicios necesarios:
 * - La ruta del archivo de usuarios
 * - El gestor de usuarios para registrar o validar credenciales
 * - La interfaz de entrada/salida para interactuar con el usuario
 * - La utilidad de ficheros para comprobar la existencia y contenido del fichero
 *
 * @property rutaArchivo Ruta del archivo donde se encuentran los usuarios registrados.
 * @property gestorUsuarios Servicio encargado de la gestión de usuarios (login, alta...).
 * @property ui Interfaz para mostrar mensajes y recoger entradas del usuario.
 * @property ficheros Utilidad para operar con ficheros (leer, comprobar existencia...).
 */
class ControlAcceso(private val rutaArchivoUsuarios: String,
                    private val consola: IEntradaSalida,
                    private val servUsuarios: IServUsuarios,
                    private val fich: IUtilFicheros)
{

    /**
     * Inicia el proceso de autenticación del sistema.
     *
     * Primero verifica si hay usuarios registrados. Si el archivo está vacío o no existe,
     * ofrece al usuario la posibilidad de crear un usuario ADMIN inicial.
     *
     * A continuación, solicita credenciales de acceso en un bucle hasta que sean válidas
     * o el usuario decida cancelar el proceso.
     *
     * @return Un par (nombreUsuario, perfil) si el acceso fue exitoso, o `null` si el usuario cancela el acceso.
     */
    fun autenticar(): Pair<String, Perfil>?{
        consola.limpiarPantalla()

        if (!verificarFicheroUsuarios()) return null

        return iniciarSesion()
    }

    /**
     * Verifica si el archivo de usuarios existe y contiene al menos un usuario registrado.
     *
     * Si el fichero no existe o está vacío, se informa al usuario y se le pregunta si desea
     * registrar un nuevo usuario con perfil ADMIN.
     *
     * Este método se asegura de que siempre haya al menos un usuario en el sistema.
     *
     * @return `true` si el proceso puede continuar (hay al menos un usuario),
     *         `false` si el usuario cancela la creación inicial o ocurre un error.
     */
    private fun verificarFicheroUsuarios(): Boolean {
        try {
            if (!fich.existeFichero(rutaArchivoUsuarios) || fich.leerArchivo(rutaArchivoUsuarios).isEmpty()){
                consola.mostrar("No hay usuarios registrados")

                if (consola.preguntar("¿Deseas crear un usuario administrador?")){
                    val nombre = consola.pedirInfo("Introduce el nombre del usuario: ")
                    val clave = consola.pedirInfo("Introduce el clave del usuario: ")

                    if (servUsuarios.agregarUsuario(nombre,clave,Perfil.ADMIN)){
                        consola.mostrar("El usuario administrador $nombre a sido credado correctamente.")
                        return true
                    }
                    else{
                        consola.mostrarError("No se a podido crear el usuario.")
                        return false
                    }
                }
            }
        }catch (e: Exception){"Error al verificar el fichero: ${e.message}"}
        return false
    }

    /**
     * Solicita al usuario sus credenciales (usuario y contraseña) en un bucle hasta
     * que sean válidas o el usuario decida cancelar.
     *
     * Si la autenticación es exitosa, se retorna el nombre del usuario y su perfil.
     *
     * @return Un par (nombreUsuario, perfil) si las credenciales son correctas,
     *         o `null` si el usuario decide no continuar.
     */
    private fun iniciarSesion(): Pair<String,Perfil>? {
        consola.mostrar("\n----- Inicio de Sesión ------")
        val nombre = consola.pedirInfo("Usuario: ")
        val clave = consola.pedirInfoOculta("Contraseña: ")

        val perfil = servUsuarios.iniciarSesion(nombre, clave)

        return if (perfil == null) {
            consola.mostrarError("Usuario o contraseña incorrectos, intentelo de nuevo.")
            return null
        }else {
            consola.mostrar("\nSesión iniciada correctamente, $nombre")
            nombre to perfil
        }
    }

}

/* fun verificarAcceso(): Perfil? {
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
    }*/