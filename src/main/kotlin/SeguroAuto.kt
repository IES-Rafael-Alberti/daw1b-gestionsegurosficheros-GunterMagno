class SeguroAuto(numPoliza: Int,dniTitular: String, importe: Double,val descripcion: String,val combustible: String,val tipoAuto: TipoAuto,val tipoCobertura: String,val asistenciaCarretera: Boolean,val numPartes: Int):
    Seguro(numPoliza, dniTitular, importe) {

    //CONSTRUCTOR SECUNDARIO LLAMANDO AL SUPER
    constructor() : this(numPoliza, dniTitular, importe, descripcion, combustible, tipoAuto, tipoCobertura, asistenciaCarretera, numPartes)

    private val tipoSeguro = tipoSeguro()
    private val id = generarID(tipoSeguro)

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        val argumento = if (numPartes > 0) { interes + (2 * numPartes) } else interes
        return obtenerImporte() * (1 + (argumento / 100))
    }

    override fun tipoSeguro(): String {
        return "Seguro de Auto"
    }

    override fun serializar(): String {
        return "$id;$dniTitular;$numPoliza;${obtenerImporte()};$descripcion;$combustible;$tipoAuto;$tipoCobertura;$asistenciaCarretera;$numPartes;$tipoSeguro"
    }
}