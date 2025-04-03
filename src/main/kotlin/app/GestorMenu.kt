package app

import model.*
import service.IServSeguros
import service.IServUsuarios
import ui.IEntradaSalida
import java.time.LocalDate

/**
 * Clase encargada de gestionar el flujo de menús y opciones de la aplicación,
 * mostrando las acciones disponibles según el perfil del usuario autenticado.
 *
 * @property nombreUsuario Nombre del usuario que ha iniciado sesión.
 * @property perfilUsuario Perfil del usuario: admin, gestion o consulta.
 * @property consola Interfaz de usuario.
 * @property gestorUsuarios Servicio de operaciones sobre usuarios.
 * @property gestorSeguros Servicio de operaciones sobre seguros.
 */
class GestorMenu(
    private val nombreUsuario: String,
    private val perfilUsuario: Perfil,
    private val consola: IEntradaSalida,
    private val gestorUsuarios: IServUsuarios,
    private val gestorSeguros: IServSeguros
) {

        /**
         * Inicia un menú según el índice correspondiente al perfil actual.
         *
         * @param indice Índice del menú que se desea mostrar (0 = principal).
         */
        fun iniciarMenu(indice: Int = 0) {
            val (opciones, acciones) = ConfiguracionesApp.obtenerMenuYAcciones(perfilUsuario.toString(), indice)
            ejecutarMenu(opciones, acciones)
        }

        /**
         * Formatea el menú en forma numerada.
         */
        private fun formatearMenu(opciones: List<String>): String {
            var cadena = ""
            opciones.forEachIndexed { index, opcion ->
                cadena += "${index + 1}. $opcion\n"
            }
            return cadena
        }

        /**
         * Muestra el menú limpiando pantalla y mostrando las opciones numeradas.
         */
        private fun mostrarMenu(opciones: List<String>) {
            consola.limpiarPantalla()
            consola.mostrar(formatearMenu(opciones), salto = false)
        }

        /**
         * Ejecuta el menú interactivo.
         *
         * @param opciones Lista de opciones que se mostrarán al usuario.
         * @param ejecutar Mapa de funciones por número de opción.
         */
        private fun ejecutarMenu(opciones: List<String>, ejecutar: Map<Int, (GestorMenu) -> Boolean>) {
            do {
                mostrarMenu(opciones)
                val opcion = consola.pedirInfo("Elige opción > ").toIntOrNull()
                if (opcion != null && opcion in 1..opciones.size) {
                    // Buscar en el mapa las acciones a ejecutar en la opción de menú seleccionada
                    val accion = ejecutar[opcion]
                    // Si la accion ejecutada del menú retorna true, debe salir del menú
                    if (accion != null && accion(this)) return
                }
                else {
                    consola.mostrarError("Opción no válida!")
                }
            } while (true)
        }

        /** Crea un nuevo usuario solicitando los datos necesarios al usuario */
        fun nuevoUsuario() {
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


            val perfil = when (consola.pedirInfo("Seleccione perfil: ")) {
                "1" -> Perfil.ADMIN
                "2" -> Perfil.GESTION
                "3" -> Perfil.CONSULTA
                else -> {
                    consola.mostrarError("Opción inválida")
                    consola.pausar()
                    return
                }
            }

            if (gestorUsuarios.agregarUsuario(nombre, clave, perfil)) {
                consola.mostrar("\nUsuario creado con éxito")
                consola.pausar()
            } else {
                consola.mostrarError("Error al crear el usuario.")
                consola.pausar()
            }
        }

        /** Elimina un usuario si existe */
        fun eliminarUsuario() {
            consola.mostrar("----- Eliminar Usuario -----")
            val nombre = consola.pedirInfo("Nombre de usuario a eliminar: ")

            if (nombre == nombreUsuario) {
                consola.mostrarError("No puede eliminarse a sí mismo")
                consola.pausar()
            }

            return if (gestorUsuarios.eliminarUsuario(nombre)) {
                consola.mostrar("\nUsuario eliminado con éxito")
                consola.pausar()
            } else {
                consola.mostrarError("Error al eliminar el usuario.")
                consola.pausar()
            }
        }

        /** Cambia la contraseña del usuario actual */
        fun cambiarClaveUsuario() {
            consola.mostrar("----- Cambiar Constraseña -----")

            val nombre = consola.pedirInfo("Nombre de usuario: ")
            val usuario = gestorUsuarios.buscarUsuario(nombre)

            if (usuario == null){
                consola.mostrarError("Usuario no encontrado")
            }

            val claveActual = consola.pedirInfoOculta("Contraseña actual: ")

            if (gestorUsuarios.iniciarSesion(nombre, claveActual) == null) {
                consola.mostrarError("Contraseña actual incorrecta")
            }

            val nuevaClave = consola.pedirInfoOculta("Nueva contraseña: ")
            val confirmacion = consola.pedirInfoOculta("Confirme nueva contraseña: ")

            if (nuevaClave != confirmacion) {
                consola.mostrarError("Las contraseñas no coinciden")
                consola.pausar()
            }

            if (usuario !== null){
                if (gestorUsuarios.cambiarClave(usuario, nuevaClave)) {
                    consola.mostrar("\nContraseña cambiada con éxito")
                    consola.pausar()
                } else {
                    consola.mostrarError("Error al cambiar la contraseña")
                    consola.pausar()
                }
            }
        }

        /**
         * Mostrar la lista de usuarios (Todos o filstrados por un perfil)
         */
        fun consultarUsuarios() {
            consola.mostrar("----- Lista de Usuarios -----")
            val usuarios = gestorUsuarios.consultarTodos()

            if (usuarios.isEmpty()) {
                consola.mostrar("No hay usuarios registrados")
            } else {
                usuarios.forEach { usuario ->
                    consola.mostrar(usuario.toString())
                }
            }

            consola.pausar()
        }

        /**
         * Solicita al usuario un DNI y verifica que tenga el formato correcto: 8 dígitos seguidos de una letra.
         *
         * @return El DNI introducido en mayúsculas.
         */
        private fun pedirDni(): String {
            return consola.pedirInfo("DNI del titular: ", "DNI inválido", Seguro::validarDni)
        }

        /**
         * Solicita al usuario un importe positivo, usado para los seguros.
         *
         * @return El valor introducido como `Double` si es válido.
         */
        private fun pedirImporte(): Double {
            return consola.pedirDouble("Importe anual: ", "Importe inválido", "Debe ser un número") { it > 0 }
        }

        /** Crea un nuevo seguro de hogar solicitando los datos al usuario */
        fun contratarSeguroHogar() {
            consola.mostrar("----- Seguro de Hogar -----")
            val dni = pedirDni()
            val importe = pedirImporte()
            val metros = consola.pedirEntero("Metros cuadrados: ", "Valor inválido", "Debe ser un número") { it > 0 }
            val valor = consola.pedirDouble("Valor contenido: ", "Valor inválido", "Debe ser un número") { it > 0 }
            val direccion = consola.pedirInfo("Dirección: ","Error al pedir la dirección") { it.isNotBlank() }
            val anio = consola.pedirEntero("Año construcción: ", "Año inválido", "Debe ser un número") { it > 0 }

            if (gestorSeguros.contratarSeguroHogar(dni, importe, metros, valor, direccion, anio)) {
                consola.mostrar("\nSeguro de hogar contratado con éxito")
                consola.pausar()
            } else {
                consola.mostrarError("Error al contratar el seguro")
                consola.pausar()
            }

        }

        /** Crea un nuevo seguro de auto solicitando los datos al usuario */
        fun contratarSeguroAuto() {
            consola.mostrar("----- Seguro de Auto -----")
            val dni = consola.pedirInfo("DNI del titular: ", "DNI inválido", Seguro::validarDni)
            val importe = consola.pedirDouble("Importe anual: ", "Importe inválido", "Debe ser un número") { it > 0 }
            val descripcion = consola.pedirInfo("Descripción del vehículo: ","Error al pedir la descripción") { it.isNotBlank() }
            val combustible = consola.pedirInfo("Combustible: ","Error al pedir el combustible") { it.isNotBlank() }

            consola.mostrar("\nTipos de auto:")
            consola.mostrar("\n1. Coche")
            consola.mostrar("\n2. Camión")
            consola.mostrar("\n3. Moto")


            val tipoAuto = when(consola.pedirInfo("Seleccione tipo de seguro: ").trim()){
                "1" -> TipoAuto.COCHE
                "2" -> TipoAuto.COCHE
                "3" -> TipoAuto.COCHE
                else -> {
                    consola.mostrarError("Error al seleccionar tipo de auto.")
                    consola.pausar()
                    return
                }
            }

            consola.mostrar("\nCoberturas disponibles:")

            val cobertura = when(consola.pedirInfo("Seleccione tipo de seguro: ").trim()){
                "1" -> Cobertura.TERCEROS
                "2" -> Cobertura.TERCEROS_AMPLIADO
                "3" -> Cobertura.FRANQUICIA_200
                "4" -> Cobertura.FRANQUICIA_300
                "5" -> Cobertura.FRANQUICIA_400
                "6" -> Cobertura.FRANQUICIA_500
                "7" -> Cobertura.TODO_RIESGO
                else -> {
                    consola.mostrarError("Error al seleccionar tipo de cobertura.")
                    consola.pausar()
                    return
                }
            }

            val asistencia = consola.preguntar("¿Incluir asistencia en carretera?")
            val partes = consola.pedirEntero("Número de partes último año: ", "Valor inválido", "Debe ser un número") { it >= 0 }

            if (gestorSeguros.contratarSeguroAuto(dni, importe, descripcion, combustible, tipoAuto, cobertura, asistencia, partes)) {
                consola.mostrar("\nSeguro de auto contratado con éxito")
                consola.pausar()
            } else {
                consola.mostrarError("Error al contratar el seguro")
                consola.pausar()
            }
        }

        /** Crea un nuevo seguro de vida solicitando los datos al usuario */
        fun contratarSeguroVida() {
            consola.mostrar("----- Seguro de Vida -----")
            val dni = consola.pedirInfo("DNI del titular: ", "DNI inválido", Seguro::validarDni)
            val importe = consola.pedirDouble("Importe anual: ", "Importe inválido", "Debe ser un número") { it > 0 }
            val fechaNac = consola.pedirInfo("Fecha nacimiento (AAAA-MM-DD): ")

            consola.mostrar("\nNiveles de riesgo:")
            consola.mostrar("\n1. Nivel de riesgo Bajo")
            consola.mostrar("\n2. Nivel de riesgo Medio")
            consola.mostrar("\n3. Nivel de riesgo Alto")
            val riesgo = when(consola.pedirInfo("Seleccione tipo de seguro: ").trim()){
                "1" -> NivelRiesgo.BAJO
                "2" -> NivelRiesgo.MEDIO
                "3" -> NivelRiesgo.ALTO
                else -> {
                    consola.mostrarError("Error al seleccionar tipo de riesgo.")
                    consola.pausar()
                    return
                }
            }

            val indemnizacion = consola.pedirDouble("Indemnización: ", "Valor inválido", "Debe ser un número") { it > 0 }

            if (gestorSeguros.contratarSeguroVida(dni, importe, LocalDate.parse(fechaNac), riesgo, indemnizacion)) {
                consola.mostrar("\nSeguro de vida contratado con éxito")
                consola.pausar()
            } else {
                consola.mostrarError("Error al contratar el seguro")
                consola.pausar()
            }
        }

        /** Elimina un seguro si existe por su número de póliza */
        fun eliminarSeguro() {
            consola.mostrar("----- Eliminar Seguro -----")
            val numPoliza = consola.pedirEntero("Número de póliza: ", "Número inválido", "Debe ser un número") { it > 0 }

            if (gestorSeguros.eliminarSeguro(numPoliza)) {
                consola.mostrar("\nSeguro eliminado con éxito")
                consola.pausar()
            } else {
                consola.mostrarError("Error al eliminar el seguro.")
                consola.pausar()
            }
        }

        /** Muestra todos los seguros existentes */
        fun consultarSeguros() {

            consola.limpiarPantalla()
            consola.mostrar("----- Lista de Seguros -----")

            val seguros = gestorSeguros.consultarTodos()


            seguros.forEach { seguro ->
                    consola.mostrar("\n${seguro.tipoSeguro()}:")
                    consola.mostrar(seguro.toString())
            }
            consola.pausar()
        }

        /** Muestra todos los seguros de tipo hogar */
        fun consultarSegurosHogar() {
            consola.limpiarPantalla()
            consola.mostrar("----- Lista de Seguros de Hogar -----")

            val seguros = gestorSeguros.consultarPorTipo("Seguro de Hogar")


            seguros.forEach { seguro ->
                consola.mostrar("Seguros de Hogar contratados:")
                consola.mostrar(seguro.toString())
            }
            consola.pausar()
        }

        /** Muestra todos los seguros de tipo auto */
        fun consultarSegurosAuto() {
            consola.limpiarPantalla()
            consola.mostrar("----- Lista de Seguros de Auto -----")

            val seguros = gestorSeguros.consultarPorTipo("Seguro de Auto")


            seguros.forEach { seguro ->
                consola.mostrar("Seguros de Auto contratados:")
                consola.mostrar(seguro.toString())
            }
            consola.pausar()
        }

        /** Muestra todos los seguros de tipo vida */
        fun consultarSegurosVida() {
            consola.limpiarPantalla()
            consola.mostrar("----- Lista de Seguros de Vida -----")

            val seguros = gestorSeguros.consultarPorTipo("Seguro de Vida")


            seguros.forEach { seguro ->
                consola.mostrar("Seguros de Vida contratados:")
                consola.mostrar(seguro.toString())
            }
            consola.pausar()
        }
    }