class SeguroVida(numPoliza: Int,dniTitular: String, importe: Double,val fechaNac: String, val nivelRiesgo: NivelRiesgo, val indemnizacion: Double):
    Seguro(numPoliza, dniTitular, importe) {

    //CONSTRUCTOR SECUNDARIO LLAMANDO AL SUPER
    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        TODO("Not yet implemented")
    }

    override fun tipoSeguro(): String {
        return "Seguro de Vida"
    }

    override fun serializar(): String {
        TODO("Not yet implemented")
    }
}