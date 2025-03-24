class SeguroHogar(numPoliza: Int,dniTitular: String, importe: Double,val metros: Int, val valorContenido: Double, val direccion: String):
    Seguro(numPoliza, dniTitular, importe) {

    //CONSTRUCTOR SECUNDARIO LLAMANDO AL SUPER

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        TODO("Not yet implemented")
    }

    override fun tipoSeguro(): String {
        return "Seguro de Hogar"
    }

    override fun serializar(): String {
        TODO("Not yet implemented")
    }

}