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
        else {
            println("Has iniciado exitosamente como Admin.\n")
            menuAdmin()
        }
    }

    fun menuAdmin(){
        println("\n1.Usuarios\n\t1. Nuevo Usuario\n\t2. Eliminar Usuario\n\t3. Cambiar contraseña\n2. Seguros\n\t1. Contratar Seguro\n\t\t1. Hogar\n\t\t2. Coche\n\t\t3. Moto\n\t2. Editar Seguro(ingresar ID)\n\t3. Eliminar Seguro\n\t4. Listar Seguros\n4. Salir")
        print("Elija una opción -> ")

        when(readlnOrNull()?.toInt()){
            1 -> {
                println("Has entrado en la seccion Usuarios, que te gustaría hacer:\n\t1. Nuevo Usuario\n\t2. Eliminar Usuario\n\t3. Cambiar contraseña")
            }
            2 -> {
                println("Has entrado en la seccion Seguros, que te gustaría hacer:\n\t1.Contratar Seguro\n\t\t1.Hogar\n\t\tAuto\n\t\tVida\n\t2.Eliminar Seguro\n\t3.Consultar Seguro\n\t\t1. Todos\n\t\t2. Hogar\n\t\t3. Auto\n\t\t4. Vida\n\t3. Salir")
            }
            3 -> return
            else -> println("Opcion incorrecta. Intentelo de nuevo")
        }
    }

    fun menuConsulta(){
        println("\n1. Seguros\n\t1. Consultar\n\t\t1.Hogar\n\t\t2. Auto\n\t\t3. Vida\n2. Mis Datos(nose si se puede hacer)\n3. Salir")
        print("Elija una opción -> ")

        when(readlnOrNull()?.toInt()){
            1 -> return//ToDo listarSeguros()
            2 -> return
            else -> println("Opcion incorrecta. Intentelo de nuevo")
        }
    }

    fun menuPrincipal(){

        mostrarTitulo()

        var almacenamiento: Boolean? = null
        while (almacenamiento == null){
            println("\nAntes de iniciar el programa necesito saber que modo de ejecución quieres:\n1. Ejecutar en modo simulación\n2. Ejecutar en modo Almacenamiento")
            print("Elija una opción -> ")

            when(readlnOrNull()?.toInt()){
                1 -> almacenamiento = false
                2 -> almacenamiento = true
                else -> println("Opcion incorrecta. Intentelo de nuevo")
            }
        }

        val rutaArchivoUsuarios = "Usuarios.txt"

        if (File(rutaArchivoUsuarios).exists() || !File(rutaArchivoUsuarios).startsWith("")){
            println("1. Iniciar sesion como Administrador\n2. Iniciar sesion como Usuario")
            print("Elija una opción -> ")

            when(readlnOrNull()?.toInt()){
                1 -> iniciarSesion("admin")
                2 -> iniciarSesion("usuario")
                else -> println("Opcion incorrecta. Intentelo de nuevo")
            }
        }
        else{
            val archivoUsuarios = File(rutaArchivoUsuarios)
            println("\nNo se encontraron usuarios en el sistema. ¿Desea crear un usuario admin?\n1. Si\n2. No")
            print("Elija una opción -> ")
            when(readlnOrNull()?.toInt()){
                1 -> registrarUsuario()
                2 -> return
                else -> println("Opcion incorrecta. Intentelo de nuevo")
            }
        }
    }
}