package ui

import jdk.internal.org.jline.reader.EndOfFileException
import jdk.internal.org.jline.reader.LineReaderBuilder
import jdk.internal.org.jline.reader.UserInterruptException
import jdk.internal.org.jline.terminal.TerminalBuilder

class Consola: IEntradaSalida {

    override fun mostrar(msj: String, salto: Boolean, pausa: Boolean) {
        print(msj)
        if (salto) println("")
        if (pausa) pausar()
    }

    override fun mostrarError(msj: String, pausa: Boolean) {
        if (msj.startsWith("Error - ")) println(msj)
        else println("Error - $msj")
    }

    override fun pedirInfo(msj: String): String {
        if(msj.trim().isNotEmpty()) mostrar(msj.trim(),false)
        return readln().trim()
    }

    override fun pedirInfo(msj: String, error: String, debeCumplir: (String) -> Boolean): String {
        val valor = pedirInfo(msj)
        require(!debeCumplir(valor)){mostrarError(error)}
        return valor
    }

    override fun pedirDouble(prompt: String,error: String,errorConv: String,debeCumplir: (Double) -> Boolean): Double {
        var valor: Double?
        do {

            try {
                valor = pedirInfo(prompt).replace(',', '.').toDouble()

                try {
                    require(!debeCumplir(valor)) {mostrarError(error)}
                } catch (e: IllegalArgumentException){
                    valor = null
                }

            } catch (e: NumberFormatException) {
                mostrarError(errorConv)
                valor = null
            }

        }while (valor == null)

        return valor
    }

    override fun pedirEntero(prompt: String, error: String, errorConv: String, debeCumplir: (Int) -> Boolean): Int {
        var valor: Int?
        do {
            try {
                valor = pedirInfo(prompt).replace(',', '.').toInt()

                try {
                    require(!debeCumplir(valor)) {mostrarError(error)}
                } catch (e: IllegalArgumentException){
                    valor = null
                }

            } catch (e: NumberFormatException) {
                mostrarError(errorConv)
                valor = null
            }

        }while (valor == null)

        return valor
    }


    override fun pedirInfoOculta(prompt: String): String {
        return try {
            val terminal = TerminalBuilder.builder()
                .dumb(true) // Para entornos no interactivos como IDEs
                .build()

            val reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build()

            reader.readLine(prompt, '*') // Oculta la contraseña con '*'
        } catch (e: UserInterruptException) {
            mostrarError("Entrada cancelada por el usuario (Ctrl + C).", pausa = false)
            ""
        } catch (e: EndOfFileException) {
            mostrarError("Se alcanzó el final del archivo (EOF ó Ctrl+D).", pausa = false)
            ""
        } catch (e: Exception) {
            mostrarError("Problema al leer la contraseña: ${e.message}", pausa = false)
            ""
        }
    }

    override fun pausar(msj: String) {
        pedirInfo(msj)
    }

    override fun limpiarPantalla(numSaltos: Int) {
        if (System.console() != null) {
            mostrar("\u001b[H\u001b[2J", false)
            System.out.flush()
        } else {
            repeat(numSaltos) {
                mostrar("")
            }
        }
    }

    override fun preguntar(mensaje: String): Boolean {
        var resp: String
        do {
            resp = try {
                pedirInfo(mensaje, "La respuesta tiene que ser si/no o en su defecto s/n") { it.lowercase() in listOf("si", "s", "no", "n") }
                //pedirInfo(mensaje, "La respuesta tiene que ser si/no o en su defecto s/n", ::debeCumplirSiNo)
            } catch (e: IllegalArgumentException) {
                mostrarError(e.message.toString())
                ""
            }.lowercase()
        } while (resp.isEmpty())

        return (resp == "si" || resp == "s")
    }

    /*override fun preguntar(mensaje: String): Boolean {
        var valor: String? = null
        do {
            if (valor != null) mostrar("La respuesta tiene que ser si/no o en su defecto s/n")
            valor = pedirInfo(mensaje)
        } while (valor != "si" && valor != "s" && valor != "no" && valor != "n")

        return if (valor == "si" || valor == "s") true else false
    }

    fun debeCumplirSiNo(valor:  String): Boolean {
        return valor.lowercase() in listOf("si", "s", "no", "n")
    }*/
}