class SeguroHogar(numPoliza: Int,dniTitular: String, importe: Double,val metros: Int, val valorContenido: Double, val direccion: String):
    Seguro(numPoliza, dniTitular, importe) {

    //CONSTRUCTOR SECUNDARIO LLAMANDO AL SUPER
    constructor() : this(numPoliza, dniTitular, importe, metros, valorContenido, direccion)

    private val tipoSeguro = tipoSeguro()
    private val id = generarID(tipoSeguro)

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        return obtenerImporte() * (1 + (interes / 100))
    }

    override fun tipoSeguro(): String {
        return "Seguro de Hogar"
    }

    override fun serializar(): String {
        return "$id;$dniTitular;$numPoliza;${obtenerImporte()};$metros;$valorContenido;$direccion;$tipoSeguro"
    }

}