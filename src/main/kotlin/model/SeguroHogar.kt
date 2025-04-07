package model

class SeguroHogar : Seguro {

    //CONSTRUCTOR SECUNDARIO LLAMANDO AL SUPER
    private var metrosCuadrados: Int = 0
    private var valorContenido: Double = 0.0
    private var direccion: String = ""
    private var anioConstruccion: Int = 0

    constructor(
        dniTitular: String,
        importe: Double,
        metrosCuadrados: Int,
        valorContenido: Double,
        direccion: String,
        anioConstruccion: Int
    ) : super(generarID(), dniTitular, importe) {
        this.metrosCuadrados = metrosCuadrados
        this.valorContenido = valorContenido
        this.direccion = direccion
        this.anioConstruccion = anioConstruccion
    }

    // Constructor interno para seguros existentes (usado al cargar desde archivo)
    private constructor(
        numPoliza: Int,
        dniTitular: String,
        importe: Double,
        metrosCuadrados: Int,
        valorContenido: Double,
        direccion: String,
        anioConstruccion: Int
    ) : super(numPoliza, dniTitular, importe) {
        this.metrosCuadrados = metrosCuadrados
        this.valorContenido = valorContenido
        this.direccion = direccion
        this.anioConstruccion = anioConstruccion
    }

    private val tipoSeguro = tipoSeguro()
    private val id = generarID()

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        return importe * (1 + (interes / 100))
    }

    override fun serializar(separador: String): String {
        return super.serializar(separador) + "$separador$metrosCuadrados$separador$valorContenido$separador$direccion$separador$tipoSeguro$separador$anioConstruccion"
    }

    override fun toString(): String {
        return "Seguro de Hogar(NºPoliza: $numPoliza, DNI Titular: ${getDNI()}, Importe: $importe, Metros Cuadrados: $metrosCuadrados, Direccion: $direccion, Valor Contenido: $valorContenido, Año Construcción: $anioConstruccion)"
    }

    companion object{
        const val PORCENTAJE_INCREMENTO_ANIOS = 0.02
        const val CICLO_ANIOS_INCREMENTO = 5

        var numPolizasHogar = 99999
        fun generarID(): Int{
            return (numPolizasHogar++)
        }

        fun crearSeguro(datos: List<String>): SeguroHogar {
            require(datos.size == 8) { "Datos incorrectos para crear SeguroHogar. Se esperaban 8 valores pero se recibieron ${datos.size}" }

            return SeguroHogar(
                numPoliza = datos[0].toInt(),
                dniTitular = datos[1],
                importe = datos[2].toDouble(),
                metrosCuadrados = datos[3].toInt(),
                valorContenido = datos[4].toDouble(),
                direccion = datos[5],
                anioConstruccion = datos[6].toInt()
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
                dniTitular = dniTitular,
                importe = importe,
                metrosCuadrados = metrosCuadrados,
                valorContenido = valorContenido,
                direccion = direccion,
                anioConstruccion = anioConstruccion.toInt()
            )
        }
    }
}