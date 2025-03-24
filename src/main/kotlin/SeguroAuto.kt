class SeguroAuto(numPoliza: Int,dniTitular: String, importe: Double,val descripcion: String,val combustible: String,val tipoAuto: TipoAuto,val tipoCobertura: String,val asistenciaCarretera: Boolean,val numPartes: Int):
    Seguro(numPoliza, dniTitular, importe) {

    //CONSTRUCTOR SECUNDARIO LLAMANDO AL SUPER
    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        TODO("Not yet implemented")
    }

    override fun tipoSeguro(): String {
        return "Seguro de Auto"
    }

    override fun serializar(): String {
        TODO("Not yet implemented")
    }
}