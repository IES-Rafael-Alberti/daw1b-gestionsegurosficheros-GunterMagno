package app

import model.Perfil
import model.Usuario
import service.IServSeguros
import service.IServUsuarios
import ui.IEntradaSalida

class GestorMenu(
    private val nombreUsuario: String,
    private val perfil: Perfil,
    private val consola: IEntradaSalida,
    private val servUsuarios: IServUsuarios,
    private val servSeguros: IServSeguros
) {

    private fun mostrarTitulo(){
        val titulo = "Gestor de Seguros"

        println(" "+"-".repeat(29))
        println("|" + " ".repeat(29) + "|")
        println("|" + " ".repeat(6)+ titulo + " ".repeat(6) + "|")
        println("|" + " ".repeat(29) + "|")
        println(" "+"-".repeat(29))
    }

    fun mostrarMenuPrincipal() {
        while (true) {
            consola.limpiarPantalla()
            mostrarTitulo()
            consola.mostrar("Sesion iniciada -> Usuario: $nombreUsuario ($perfil)")

            when (perfil) {
                Perfil.ADMIN -> if (!menuAdmin()) break
                Perfil.GESTION -> if (!menuGestion()) break
                Perfil.CONSULTA -> if (!menuConsulta()) break
            }
        }
    }

    fun seleccionarModoAlmacenamiento(): Boolean {
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

    private fun menuAdmin(): Boolean {
        consola.mostrar("\n1. Usuarios")
        consola.mostrar("2. Seguros")
        consola.mostrar("3. Salir")

        return when (consola.pedirInfo("Seleccione una opción: ")) {
            "1" -> menuUsuarios()
            "2" -> menuSeguros()
            "3" -> false
            else -> {
                consola.mostrarError("Opción inválida")
                true
            }
        }
    }

    private fun menuGestion(): Boolean {
        consola.mostrar("\n1. Seguros")
        consola.mostrar("2. Salir")

        return when (consola.pedirInfo("Seleccione una opción: ")) {
            "1" -> menuSeguros()
            "2" -> false
            else -> {
                consola.mostrarError("Opción inválida")
                true
            }
        }
    }

    private fun menuConsulta(): Boolean {
        consola.mostrar("\n1. Consultar seguros")
        consola.mostrar("2. Salir")

        return when (consola.pedirInfo("Seleccione una opción: ")) {
            "1" -> consultarSeguros()
            "2" -> false
            else -> {
                consola.mostrarError("Opción inválida")
                true
            }
        }
    }

    private fun menuUsuarios(): Boolean {
        consola.mostrar("----- Gestión Usuarios -----")
        consola.mostrar("1. Nuevo usuario")
        consola.mostrar("2. Eliminar usuario")
        consola.mostrar("3. Cambiar contraseña")
        consola.mostrar("4. Consultar usuarios")
        consola.mostrar("5. Volver")

        return when (consola.pedirInfo("Seleccione una opción: ")) {
            "1" -> nuevoUsuario()
            "2" -> eliminarUsuario()
            "3" -> cambiarClaveUsuario()
            "4" -> consultarUsuarios()
            "5" -> true
            else -> {
                consola.mostrarError("Opción inválida")
                true
            }
        }
    }

    private fun menuSeguros(): Boolean {
        consola.mostrar("----- Gestión Seguros -----")
        consola.mostrar("1. Contratar seguro")
        consola.mostrar("2. Eliminar seguro")
        consola.mostrar("3. Consultar seguros")
        consola.mostrar("4. Volver")

        return when (consola.pedirInfo("Seleccione una opción: ")) {
            "1" -> contratarSeguro()
            "2" -> eliminarSeguro()
            "3" -> consultarSeguros()
            "4" -> true
            else -> {
                consola.mostrarError("Opción inválida")
                true
            }
        }
    }

    private fun nuevoUsuario(): Boolean {
        consola.mostrar("----- Crear Usuario -----")

        var nombre = ""
        while (nombre.isBlank()){
            nombre = consola.pedirInfo("Nombre de usuario: ", "El nombre no es valido") { it.isNotBlank() }
            if (nombre.isBlank()){
                consola.mostrar("El nombre no es valido")
            }
        }

        val clave = consola.pedirInfoOculta("Contraseña: ")

        consola.mostrar("\nPerfiles disponibles:")
        consola.mostrar("1. ADMIN")
        consola.mostrar("2. GESTION")
        consola.mostrar("3. CONSULTA")

        var opcion2 = false
        while (!opcion2){
            val perfil = when (consola.pedirInfo("Seleccione perfil: ")) {
                "1" -> {
                    Perfil.ADMIN
                    opcion2 = true
                }
                "2" -> {
                    Perfil.GESTION
                    opcion2 = true
                }
                "3" -> {
                    Perfil.CONSULTA
                    opcion2 = true
                }
                else -> {
                    consola.mostrarError("Opción inválida")
                }
            }
        }

        return if (servUsuarios.agregarUsuario(nombre, clave, perfil)) {
            consola.mostrar("\nUsuario creado con éxito")
            consola.pausar()
            true
        } else {
            consola.mostrarError("Error al crear el usuario.")
            consola.pausar()
            true
        }
    }

    private fun eliminarUsuario(): Boolean {
        consola.mostrar("----- Eliminar Usuario -----")
        val nombre = consola.pedirInfo("Nombre de usuario a eliminar: ")

        if (nombre == nombreUsuario) {
            consola.mostrarError("No puede eliminarse a sí mismo")
            consola.pausar()
            return true
        }

        return if (servUsuarios.eliminarUsuario(nombre)) {
            consola.mostrar("\nUsuario eliminado con éxito")
            consola.pausar()
            true
        } else {
            consola.mostrarError("Error al eliminar el usuario. ¿Existe?")
            consola.pausar()
            true
        }
    }

    private fun cambiarClaveUsuario(): Boolean {
        consola.mostrar("----- Cambiar Constraseña -----")

        val nombre = consola.pedirInfo("Nombre de usuario: ")
        val usuario = servUsuarios.buscarUsuario(nombre)

        if (usuario == null){
            consola.mostrarError("Usuario no encontrado")
            return false
        }

        val claveActual = consola.pedirInfoOculta("Contraseña actual: ")

        if (servUsuarios.iniciarSesion(nombre, claveActual) == null) {
            consola.mostrarError("Contraseña actual incorrecta")
            return false
        }

        val nuevaClave = consola.pedirInfoOculta("Nueva contraseña: ")
        val confirmacion = consola.pedirInfoOculta("Confirme nueva contraseña: ")

        if (nuevaClave != confirmacion) {
            consola.mostrarError("Las contraseñas no coinciden")
            consola.pausar()
            return false
        }

        return if (servUsuarios.cambiarClave(usuario, nuevaClave)) {
            consola.mostrar("\nContraseña cambiada con éxito")
            consola.pausar()
            true
        } else {
            consola.mostrarError("Error al cambiar la contraseña")
            consola.pausar()
            false
        }
    }

    private fun consultarUsuarios(): Boolean {
        consola.mostrar("----- Lista de Usuarios -----")
        val usuarios = servUsuarios.consultarTodos()

        if (usuarios.isEmpty()) {
            consola.mostrar("No hay usuarios registrados")
        } else {
            usuarios.forEach { usuario ->
                consola.mostrar("${usuario.nombre} - ${usuario.perfil}")
            }
        }

        consola.pausar()
        return true
    }

    private fun contratarSeguro(): Boolean {
        consola.mostrar("----- Contratar Seguro -----")
        consola.mostrar("1. Seguro de Hogar")
        consola.mostrar("2. Seguro de Auto")
        consola.mostrar("3. Seguro de Vida")
        consola.mostrar("4. Volver")

        return when (consola.pedirInfo("Seleccione tipo de seguro: ")) {
            "1" -> contratarSeguroHogar()
            "2" -> contratarSeguroAuto()
            "3" -> contratarSeguroVida()
            "4" -> true
            else -> {
                consola.mostrarError("Opción inválida")
                false
            }
        }
    }

    private fun contratarSeguroHogar(): Boolean {
        consola.mostrar("----- Seguro de Hogar -----")
        val dni = consola.pedirInfo("DNI del titular: ", "DNI inválido", Seguro::validarDni)
        val importe = consola.pedirDouble("Importe anual: ", "Importe inválido", "Debe ser un número") { it > 0 }
        val metros = consola.pedirEntero("Metros cuadrados: ", "Valor inválido", "Debe ser un número") { it > 0 }
        val valor = consola.pedirDouble("Valor contenido: ", "Valor inválido", "Debe ser un número") { it > 0 }
        val direccion = consola.pedirInfo("Dirección: ") { it.isNotBlank() }
        val anio = consola.pedirEntero("Año construcción: ", "Año inválido", "Debe ser un número") { it > 0 }

        return if (servSeguros.contratarSeguroHogar(dni, importe, metros, valor, direccion, anio)) {
            consola.mostrar("\nSeguro de hogar contratado con éxito")
            consola.pausar()
            true
        } else {
            consola.mostrarError("Error al contratar el seguro")
            consola.pausar()
            false
        }
    }

    private fun contratarSeguroAuto(): Boolean {
        mostrarTitulo("SEGURO DE AUTO")
        val dni = consola.pedirInfo("DNI del titular: ", "DNI inválido", Seguro::validarDni)
        val importe = consola.pedirDouble("Importe anual: ", "Importe inválido", "Debe ser un número") { it > 0 }
        val descripcion = consola.pedirInfo("Descripción del vehículo: ") { it.isNotBlank() }
        val combustible = consola.pedirInfo("Combustible: ") { it.isNotBlank() }

        consola.mostrar("\nTipos de auto:")
        Auto.values().forEach { consola.mostrar("${it.ordinal + 1}. ${it.name}") }
        val tipoAuto = try {
            val opcion = consola.pedirEntero(
                "Seleccione tipo (1-${Auto.values().size}): ",
                "Opción inválida",
                "Debe ser un número"
            ) { it in 1..Auto.values().size }

            Auto.values()[opcion - 1]
        } catch (e: Exception) {
            consola.mostrarError("Error al seleccionar tipo de auto: ${e.message}")
            return true
        }

        consola.mostrar("\nCoberturas disponibles:")
        Cobertura.values().forEach { consola.mostrar("${it.ordinal + 1}. ${it.desc}") }
        val cobertura = try {
            val opcion = consola.pedirEntero(
                "Seleccione cobertura (1-${Cobertura.values().size}): ",
                "Opción inválida",
                "Debe ser un número"
            ) { it in 1..Cobertura.values().size }

            Cobertura.values()[opcion - 1]
        } catch (e: Exception) {
            consola.mostrarError("Error al seleccionar cobertura: ${e.message}")
            return true
        }

        val asistencia = consola.preguntar("¿Incluir asistencia en carretera?")
        val partes = consola.pedirEntero("Número de partes último año: ", "Valor inválido", "Debe ser un número") { it >= 0 }

        return if (servSeguros.contratarSeguroAuto(dni, importe, descripcion, combustible, tipoAuto, cobertura, asistencia, partes)) {
            consola.mostrar("\nSeguro de auto contratado con éxito")
            consola.pausar()
            true
        } else {
            consola.mostrarError("Error al contratar el seguro")
            consola.pausar()
            true
        }
    }

    private fun contratarSeguroVida(): Boolean {
        mostrarTitulo("SEGURO DE VIDA")
        val dni = consola.pedirInfo("DNI del titular: ", "DNI inválido", Seguro::validarDni)
        val importe = consola.pedirDouble("Importe anual: ", "Importe inválido", "Debe ser un número") { it > 0 }
        val fechaNac = consola.pedirInfo("Fecha nacimiento (AAAA-MM-DD): ") { it.matches(Regex("\\d{4}-\\d{2}-\\d{2}")) }

        consola.mostrar("\nNiveles de riesgo:")
        Riesgo.values().forEach { consola.mostrar("${it.ordinal + 1}. ${it.name} (${it.interesAplicado}%)") }
        val riesgo = try {
            val opcion = consola.pedirEntero(
                "Seleccione nivel (1-${Riesgo.values().size}): ",
                "Opción inválida",
                "Debe ser un número"
            ) { it in 1..Riesgo.values().size }

            Riesgo.values()[opcion - 1]
        } catch (e: Exception) {
            consola.mostrarError("Error al seleccionar nivel de riesgo: ${e.message}")
            return true
        }

        val indemnizacion = consola.pedirDouble("Indemnización: ", "Valor inválido", "Debe ser un número") { it > 0 }

        return if (servSeguros.contratarSeguroVida(dni, importe, LocalDate.parse(fechaNac), riesgo, indemnizacion)) {
            consola.mostrar("\nSeguro de vida contratado con éxito")
            consola.pausar()
            true
        } else {
            consola.mostrarError("Error al contratar el seguro")
            consola.pausar()
            true
        }
    }

    private fun eliminarSeguro(): Boolean {
        mostrarTitulo("ELIMINAR SEGURO")
        val numPoliza = consola.pedirEntero("Número de póliza: ", "Número inválido", "Debe ser un número") { it > 0 }

        return if (servSeguros.eliminarSeguro(numPoliza)) {
            consola.mostrar("\nSeguro eliminado con éxito")
            consola.pausar()
            true
        } else {
            consola.mostrarError("Error al eliminar el seguro. ¿Existe?")
            consola.pausar()
            true
        }
    }

    private fun consultarSeguros(): Boolean {
        mostrarTitulo("CONSULTAR SEGUROS")
        consola.mostrar("1. Todos los seguros")
        consola.mostrar("2. Seguros de Hogar")
        consola.mostrar("3. Seguros de Auto")
        consola.mostrar("4. Seguros de Vida")
        consola.mostrar("5. Volver")

        return when (consola.pedirInfo("Seleccione opción: ")) {
            "1" -> mostrarSeguros(servSeguros.consultarTodos())
            "2" -> mostrarSeguros(servSeguros.consultarPorTipo("SeguroHogar"))
            "3" -> mostrarSeguros(servSeguros.consultarPorTipo("SeguroAuto"))
            "4" -> mostrarSeguros(servSeguros.consultarPorTipo("SeguroVida"))
            "5" -> true
            else -> {
                consola.mostrarError("Opción inválida")
                true
            }
        }
    }

    private fun mostrarSeguros(seguros: List<Seguro>): Boolean {
        consola.limpiarPantalla()
        mostrarTitulo("LISTA DE SEGUROS")

        if (seguros.isEmpty()) {
            consola.mostrar("No hay seguros registrados")
        } else {
            seguros.forEach { seguro ->
                consola.mostrar("\n${seguro.tipoSeguro()}:")
                consola.mostrar(seguro.toString())
                consola.mostrar("Importe próximo año (5% interés): ${"%.2f".format(seguro.calcularImporteAnioSiguiente(5.0))}")
            }
        }

        consola.pausar()
        return true
    }
}