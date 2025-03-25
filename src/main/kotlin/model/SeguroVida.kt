package model

class SeguroVida: Seguro {

    val fechaNac: String = ""
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

    override fun tipoSeguro(): String {
        return "model.Seguro de Vida"
    }

    override fun serializar(separador: String): String {
        return super.serializar(separador) + "$separador$fechaNac$separador$nivelRiesgo$separador$indemnizacion$separador$tipoSeguro"
    }

    companion object{
        private var numPolizasAuto = 799999
        fun generarID(): Int{
            return (numPolizasAuto++)
        }

        fun crearSeguro(datos: List<String>): SeguroVida{
            val
            return SeguroVida()
        }
    }
}