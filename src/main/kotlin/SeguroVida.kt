class SeguroVida(numPoliza: Int,dniTitular: String, importe: Double,val fechaNac: String, val nivelRiesgo: NivelRiesgo, val indemnizacion: Double):
    Seguro(numPoliza, dniTitular, importe) {

    //CONSTRUCTOR SECUNDARIO LLAMANDO AL SUPER
        constructor() : this(numPoliza, dniTitular, importe, fechaNac, nivelRiesgo, indemnizacion)

    private val tipoSeguro = tipoSeguro()
    private val id = generarID(tipoSeguro)

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        val argumento = when(nivelRiesgo) {
            NivelRiesgo.BAJO -> 2.0
            NivelRiesgo.MEDIO -> 5.0
            NivelRiesgo.ALTO -> 10.0
        }
        return obtenerImporte() * (1 + ((interes + argumento)/100))
    }

    override fun tipoSeguro(): String {
        return "Seguro de Vida"
    }

    override fun serializar(): String {
        return "$id;$dniTitular;$numPoliza;${obtenerImporte()};$fechaNac;$nivelRiesgo;$indemnizacion;$tipoSeguro"
    }
}