import ui.Consola
import app.CargadorInicial
import app.ControlAcceso
import app.GestorMenu
import data.*
import model.Seguro
import model.SeguroAuto
import model.SeguroHogar
import model.SeguroVida
import service.GestorSeguros
import service.GestorUsuarios
import utils.Ficheros
import utils.Seguridad

fun seleccionarModoAlmacenamiento(consola: Consola): Boolean {
    while (true) {
        consola.mostrar("\nSeleccione modo de almacenamiento:")
        consola.mostrar("1. Ejecutar en modo simulación (solo en memoria)")
        consola.mostrar("2. Ejecutar en modo Almacenamiento (usar ficheros)")

        when (consola.pedirInfo("Elija una opción: ")) {
            "1" -> return false
            "2" -> return true
            else -> consola.mostrarError("Opción inválida")
        }
    }
}

fun mostrarTitulo(){
    val titulo = "Gestor de Seguros"

    println(" "+"-".repeat(29))
    println("|" + " ".repeat(29) + "|")
    println("|" + " ".repeat(6)+ titulo + " ".repeat(6) + "|")
    println("|" + " ".repeat(29) + "|")
    println(" "+"-".repeat(29))
}

fun main() {

    // Crear dos variables con las rutas de los archivos de texto donde se almacenan los usuarios y seguros.
    // Estos ficheros se usarán solo si el programa se ejecuta en modo de almacenamiento persistente.

    val rutaUsuarios = "Usuarios.txt"
    val rutaSeguros = "Seguros.txt"

    // Instanciamos los componentes base del sistema: la interfaz de usuario, el gestor de ficheros y el módulo de seguridad.
    // Estos objetos serán inyectados en los diferentes servicios y utilidades a lo largo del programa.

    val consola = Consola()
    val gestorFicheros = Ficheros(consola)
    val seguridad = Seguridad()

    // Limpiamos la pantalla antes de comenzar, para que la interfaz esté despejada al usuario.

    consola.limpiarPantalla()
    mostrarTitulo()

    // Preguntamos al usuario si desea iniciar en modo simulación.
    // En modo simulación los datos no se guardarán en archivos, solo estarán en memoria durante la ejecución.

    val modoAlmacenamiento = seleccionarModoAlmacenamiento(consola)

    // Declaramos los repositorios de usuarios y seguros.
    // Se asignarán más abajo dependiendo del modo elegido por el usuario.
    // Si se ha elegido modo simulación, se usan repositorios en memoria.
    // Si se ha elegido almacenamiento persistente, se instancian los repositorios que usan ficheros.
    // Además, creamos una instancia del cargador inicial de información y lanzamos la carga desde los ficheros.

    val repoUsuarios = if(!modoAlmacenamiento)(RepoUsuariosMem()) else RepoUsuariosFich(rutaUsuarios,gestorFicheros)
    val repoSeguros = if(!modoAlmacenamiento)(RepoSegurosMem()) else RepoSegurosFich(rutaSeguros,gestorFicheros)

    if (modoAlmacenamiento && repoUsuarios is RepoUsuariosFich && repoSeguros is RepoSegurosFich) {
        val cargadorInicial = CargadorInicial(consola)

        val mapaSeguros: Map<String, (List<String>) -> Seguro> = mapOf(
            "Seguro de Hogar" to { datos -> SeguroHogar.crearSeguro(datos) },
            "Seguro de Auto" to { datos -> SeguroAuto.crearSeguro(datos) },
            "Seguro de Vida" to { datos -> SeguroVida.crearSeguro(datos) }
        )

        cargadorInicial.cargarInfo(repoUsuarios,repoSeguros,mapaSeguros)
    }

    // Se crean los servicios de lógica de negocio, inyectando los repositorios y el componente de seguridad.

    val gestorUsuarios = GestorUsuarios(repoUsuarios,seguridad)
    val gestorSeguros = GestorSeguros(repoSeguros)

    // Se inicia el proceso de autenticación. Se comprueba si hay usuarios en el sistema y se pide login.
    // Si no hay usuarios, se permite crear un usuario ADMIN inicial.

    val controlAcceso = ControlAcceso(rutaUsuarios,consola,gestorUsuarios,gestorFicheros)
    val datosUsuario = controlAcceso.autenticar()

    if (datosUsuario == null) {
        consola.mostrarError("No se pudo completar la autenticación. Saliendo del programa...")
        return
    }

    // Si el login fue exitoso (no es null), se inicia el menú correspondiente al perfil del usuario autenticado.
    // Se lanza el menú principal, **iniciarMenu(0)**, pasándole toda la información necesaria.

    val nombreUsuario = datosUsuario.first
    val perfilUsuario = datosUsuario.second

    consola.limpiarPantalla()
    consola.mostrar("¡Bienvenido, $nombreUsuario! (Perfil: $perfilUsuario)")
    val gestorMenu = GestorMenu(nombreUsuario,perfilUsuario,consola,gestorUsuarios,gestorSeguros)

    gestorMenu.iniciarMenu(0)
}

//ToDo Importante

// No funciona pedir infoOculta se queda rallao

//ToDo Importancia media



//ToDo Menos importancia


