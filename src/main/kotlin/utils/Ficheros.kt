package utils

import model.IExportable
import ui.IEntradaSalida
import java.io.File
import java.io.IOException

class Ficheros(private val consola:IEntradaSalida): IUtilFicheros {

    override fun leerArchivo(ruta: String): List<String> {
        val archivo = File(ruta)
        return try {
            if (archivo.exists()){
                archivo.readLines()
            }else{
                throw IOException("Error no se pudo leer el archivo o no existe.")
            }
        } catch (e: IOException) {
            consola.mostrarError("Error al leer el archivo: ${e.message}")
            emptyList()
        }
    }

    override fun agregarLinea(ruta: String, linea: String): Boolean {
        return try{
            File(ruta).appendText("$linea\n")
            true
        }catch (e: IOException){
            consola.mostrarError("Error al escribir en el archivo: ${e.message}")
            false
        }
    }

    override fun <T : IExportable> escribirArchivo(ruta: String, elementos: List<T>): Boolean {
        return try{
            File(ruta).writeText(elementos.joinToString("\n") {it.serializar()})
            true
        } catch (e: IOException){
            consola.mostrarError("Error al sobreescribir el archivo: ${e.message}")
            false
        }
    }

    override fun existeFichero(ruta: String): Boolean {
        return File(ruta).exists()
    }

    override fun existeDirectorio(ruta: String): Boolean {
        return File(ruta).isDirectory
    }
}