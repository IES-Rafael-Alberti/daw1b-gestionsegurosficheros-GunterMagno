package model

import java.time.LocalDate

class SeguroVida: Seguro {

    val fechaNac: LocalDate = LocalDate.now() //ToDo cambiar esto
    val nivelRiesgo: NivelRiesgo = NivelRiesgo.BAJO
    val indemnizacion: Double = 0.0

    constructor(numPoliza: Int, dniTitular: String, importe: Double) : super(numPoliza, dniTitular, importe, fechaNac, nivelRiesgo, indemnizacion)
    constructor(numPoliza: Int, dniTitular: String, importe: Double) : super(numPoliza, dniTitular, importe, fechaNac, nivelRiesgo, indemnizacion)

    private val tipoSeguro = tipoSeguro()
    private val id = generarID()

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        val argumento = when(nivelRiesgo) {
            NivelRiesgo.BAJO -> 2.0
            NivelRiesgo.MEDIO -> 5.0
            NivelRiesgo.ALTO -> 10.0
        }
        return importe * (1 + ((interes + argumento)/100))
    }

    override fun serializar(separador: String): String {
        return super.serializar(separador) + "$separador$fechaNac$separador$nivelRiesgo$separador$indemnizacion$separador$tipoSeguro"
    }

    companion object{
        var numPolizasVida = 799999
        fun generarID(): Int{
            return (numPolizasVida++)
        }

        fun crearSeguro(datos: List<String>): SeguroVida{
            require(datos.size == 7) { "Datos incorrectos para crear SeguroVida" }
            return SeguroVida(
                numPoliza = datos[0].toInt(),
                dniTitular = datos[1],
                id = datos[2].toInt(),
                importe = datos[3].toDouble(),
                fechaNac = LocalDate.parse(datos[4]),
                nivelRiesgo = NivelRiesgo.getRiesgo(datos[5]),
                indemnizacion = datos[6].toDouble()
            )
        }

        fun nuevoSeguro(
            dniTitular: String,
            importe: Double,
            fechaNacimiento: LocalDate,
            nivelRiesgo: NivelRiesgo,
            indemnizacion: Double
        ): SeguroVida {
            return SeguroVida(
                numPoliza = ++numPolizasVida,
                dniTitular = dniTitular,
                id = numPolizasVida,
                importe = importe,
                fechaNac = fechaNacimiento,
                nivelRiesgo = nivelRiesgo,
                indemnizacion = indemnizacion
            )
        }
    }
}