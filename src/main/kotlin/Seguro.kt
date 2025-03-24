abstract class Seguro(val numPoliza: Int,val dniTitular: String,private val importe: Double){

    abstract fun calcularImporteAnioSiguiente(interes: Double): Double

    abstract fun tipoSeguro(): String

    abstract fun serializar(): String

    companion object{
        private var contador = 0 // ToDo El contador tiene que ser por cada seguro o uno general??
        fun generarID(tipoSeguro: String){
            val id: String
            when(tipoSeguro){
                "Seguro de Hogar" -> {
                    id = "100000$contador"
                    contador++
                }
                "Seguro de Auto" ->{
                    id = "400000$contador"
                    contador++
                }
                "Seguro de Vida" ->{
                    id = "800000$contador"
                    contador++
                }
            }
        }

        fun validarDni(dni: String): Boolean{
            return dni.matches(Regex("^[0-9]{8}[A-Z]\$"))
        }
    }

}