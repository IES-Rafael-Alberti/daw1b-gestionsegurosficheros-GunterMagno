package model

class SeguroHogar : Seguro {

    //CONSTRUCTOR SECUNDARIO LLAMANDO AL SUPER
    private var metrosCuadrados: Int = 0
    private var valorContenido: Double = 0.0
    private var direccion: String = ""
    private var anioConstruccion: String = ""

    constructor(numPoliza: Int,dniTitular: String,importe: Double,metrosCuadrados: Int,valorContenido: Double,direccion: String,anioConstruccion: String) : super(numPoliza, dniTitular, importe) //ToDo como hacerlo

    private constructor(numPoliza: Int,dniTitular: String,importe: Double,metrosCuadrados: Int,valorContenido: Double,direccion: String,anioConstruccion: String) : super(numPoliza, dniTitular, importe) //ToDo como hacerlo

    private val tipoSeguro = tipoSeguro()
    private val id = generarID()

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        return importe * (1 + (interes / 100))
    }

    override fun tipoSeguro(): String {
        return "Seguro de Hogar"
    }

    override fun serializar(separador: String): String {
        return super.serializar(separador) + "$separador$metrosCuadrados$separador$valorContenido$separador$direccion$separador$tipoSeguro$separador$anioConstruccion"
    }

    override fun toString(): String {
        return "$numPoliza"
    }

    companion object{
        const val PORCENTAJE_INCREMENTO_ANIOS = 0.02
        const val CICLO_ANIOS_INCREMENTO = 5

        var numPolizasHogar = 99999
        fun generarID(): Int{
            return (numPolizasHogar++)
        }

        fun crearSeguro(datos: List<String>): SeguroHogar{
            require(datos.size == 8) { "Datos incorrectos para crear SeguroHogar" }
            return SeguroHogar(
                numPoliza = datos[0].toInt(),
                dniTitular = datos[1],
                id = datos[2].toInt(),
                importe = datos[3].toDouble(),
                metrosCuadrados = datos[4].toInt(),
                valorContenido = datos[5].toDouble(),
                direccion = datos[6],
                anioConstruccion = datos[7].toInt()
            )
        }

        fun nuevoSeguro(
            dniTitular: String,
            importe: Double,
            metrosCuadrados: Int,
            valorContenido: Double,
            direccion: String,
            anioConstruccion: Int
        ): SeguroHogar {
            return SeguroHogar(
                numPoliza = ++numPolizasHogar,
                dniTitular = dniTitular,
                id = numPolizasHogar,
                importe = importe,
                metrosCuadrados = metrosCuadrados,
                valorContenido = valorContenido,
                direccion = direccion,
                anioConstruccion = anioConstruccion
            )
        }
    }
}