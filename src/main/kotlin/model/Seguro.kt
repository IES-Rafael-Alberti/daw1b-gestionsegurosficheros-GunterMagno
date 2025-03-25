package model

abstract class Seguro(val numPoliza: Int,private val dniTitular: String, protected val importe: Double): IExportable{

    abstract fun calcularImporteAnioSiguiente(interes: Double): Double

    abstract fun tipoSeguro(): String

    override fun serializar(separador: String): String {
        return "$numPoliza$separador$dniTitular$separador$importe"
    }

    override fun toString(): String {
        return "Seguro (numPoliza = $numPoliza, dniTitular = $dniTitular, importe = $importe)"
    }

    override fun hashCode(): Int {
        return super.hashCode() + 1
    }

    override fun equals(other: Any?): Boolean {
        if (other == Seguro){
            return if (numPoliza == other.numPoliza){
                true
                //ToDo como hacer esto
            } else false
        }
        return super.equals(other)
    }

    companion object{
        fun validarDni(dni: String): Boolean{
            return dni.matches(Regex("^[0-9]{8}[A-Z]\$"))
        }
    }

}