import java.io.File

class Consola {

    fun mostrarTitulo(){
        val titulo = "Gestor de Seguros"

        println(" "+"-".repeat(29))
        println("|" + " ".repeat(29) + "|")
        println("|" + " ".repeat(6)+ titulo + " ".repeat(6) + "|")
        println("|" + " ".repeat(29) + "|")
        println(" "+"-".repeat(29))
    }

    fun registrarUsuario(){
        //ToDo Hacer logica para registrar un usuario
    }

    fun iniciarSesion(tipoUsuario: String){

        //ToDo hacer logica para iniciar la sesion

        if (tipoUsuario == "usuario"){
            println("Has iniciado exitosamente como Usuario.\n")
            menuConsulta()
        }
        else if (tipoUsuario == "admin"){
            println("Has iniciado exitosamente como Admin.\n")
            menuAdmin()
        }
        else if (tipoUsuario == "gestion"){
            println("Has iniciado exitosamente como Admin.\n")
            menuGestion()
        }
    }

    fun menuAdmin(){
        println("\n1.Usuarios\n\t1. Nuevo Usuario\n\t2. Eliminar Usuario\n\t3. Cambiar contraseña\n2. Seguros\n\t1. Contratar Seguro\n\t\t1. Seguro de Hogar\n\t\t2. Seguro de Coche\n\t\t3. Seguro de Moto\n\t2. Eliminar Seguro\n\t3. Consultar Seguros\n\t\t1.Todos\n\t\t2. Hogar\n\t\t3. Auto\n\t\t4.Vida\n3. Salir")
        print("Elija una opción -> ")

        when(readlnOrNull()?.toInt()){
            1 -> {
                println("Has entrado en la seccion Usuarios, que te gustaría hacer:\n\t1. Nuevo Usuario\n\t2. Eliminar Usuario\n\t3. Cambiar contraseña")
                print("Elija una opción -> ")
                when(readlnOrNull()?.toInt()){
                    1 -> return //ToDo crearUsuario
                    2 -> return //ToDo eliminarUsuario
                    3 -> return //ToDo cambiarContraseña
                }
            }
            2 -> {
                println("Has entrado en la seccion Seguros, que te gustaría hacer:\n\t1.Contratar Seguro\n\t\t1. Hogar\n\t\t2. Auto\n\t\t3. Vida\n\t2.Eliminar Seguro\n\t3.Consultar Seguro\n\t\t1. Todos\n\t\t2. Hogar\n\t\t3. Auto\n\t\t4. Vida\n\t3. Salir")
                print("Elija una opción -> ")
                when(readlnOrNull()?.toInt()){
                    1 -> {
                        println("Contratar un seguro, que tipo de seguro quieres contratar:\n\t1. Hogar\n\t2. Auto\n\t3. Vida")
                        print("Elija una opción -> ")
                        when(readlnOrNull()?.toInt()){
                            1 -> return //ToDo contratarSeguro(TipoSeguro)
                            2 -> return //ToDo contratarSeguro(TipoSeguro)
                            3 -> return //ToDo contratarSeguro(TipoSeguro)
                        }
                    }
                    2 -> {
                        println("Que seguro quieres eliminar")
                        //ToDo listarSeguros
                    }
                    3 -> return //ToDo crearUsuario
                }
            }
            3 -> return
            else -> println("Opcion incorrecta. Intentelo de nuevo")
        }
    }

    fun menuConsulta(){
        println("\n1. Seguros\n\t1. Consultar\n\t\t1. Todos\n\t\t2. Hogar\n\t\t3. Auto\n\t\t4. Vida\n2. Salir")
        print("Elija una opción -> ")

        when(readlnOrNull()?.toInt()){
            1 -> {
                println("Consultar Seguros:\n\t1. Todos\n\t2. Hogar\n\t3. Auto\n\t4. Vida")
                print("Elija una opción -> ")
                when(readlnOrNull()?.toInt()){
                    1 -> return //ToDo listarTodos()
                    2 -> return //ToDo listarSeguroHogar()
                    3 -> return //ToDo listarSeguroAuto()
                    4 -> return //ToDo listarSeguroVida()
                }
            }
            2 -> return
            else -> println("Opcion incorrecta. Intentelo de nuevo")
        }
    }

    fun menuGestion(){
        println("\n1. Seguros\n\t1. Contratar\n\t\t1. Hogar\n\t\t2. Auto\n\t\t3. Vida\n\t2. Eliminar Seguro\n\t3. Consultar\n\t\t1. Todos\n\t\t2. Hogar\n\t\t3. Auto\n\t\t4. Vida\n2. Salir")
        print("Elija una opción -> ")

        when(readlnOrNull()?.toInt()){
            1 -> {
                println("1. Contratar Seguros:\n\t1. Hogar\n\t2. Auto\n\t3. Vida\n2. Eliminar Seguro\n3. Consultar Seguro\n\t\t1. Todos\n\t\t2. Hogar\n\t\t3. Auto\n\t\t4. Vida")
                print("Elija una opción -> ")
                when(readlnOrNull()?.toInt()){
                    1 -> return //ToDo contratarSeguro(Hogar)
                    2 -> return //ToDo contratarSeguro(Auto)
                    3 -> return //ToDo contratarSeguro(Vida)
                }
            }
            2 -> return

            else -> println("Opcion incorrecta. Intentelo de nuevo")
        }
    }

    fun menuPrincipal(){

        mostrarTitulo()

        var almacenamiento: Boolean? = null
        while (almacenamiento == null){
            println("\nAntes de iniciar el programa necesito saber que modo de ejecución quieres:\n1. Ejecutar en modo simulación (solo en memoria)\n2. Ejecutar en modo Almacenamiento (usar ficheros)")
            print("Elija una opción -> ")

            when(readlnOrNull()){
                "1" -> almacenamiento = false
                "2" -> almacenamiento = true
                else -> println("Opcion incorrecta. Intentelo de nuevo")
            }
        }

        if (almacenamiento){
            val rutaArchivoUsuarios = "Usuarios.txt"

            if (!File(rutaArchivoUsuarios).exists() || File(rutaArchivoUsuarios).startsWith("")) {
                val archivoUsuarios = File(rutaArchivoUsuarios)
                println("\nNo se encontraron usuarios en el sistema. ¿Desea crear un usuario admin?\n1. Si\n2. No")
                print("Elija una opción -> ")
                when (readlnOrNull()?.toInt()) {
                    1 -> registrarUsuario()
                    2 -> return
                    else -> println("Opcion incorrecta. Intentelo de nuevo")
                }
            }
        }

        println("\n\n\n")
        mostrarTitulo()
        println("\n1. Iniciar sesion como Administrador\n2. Iniciar sesion como Gestor\n3. Iniciar sesion como Usuario")
        print("Elija una opción -> ")

        when (readlnOrNull()?.toInt()) {
            1 -> iniciarSesion("admin")
            2 -> iniciarSesion("gestion")
            3 -> iniciarSesion("usuario")
            else -> println("Opcion incorrecta. Intentelo de nuevo")
        }
    }
}