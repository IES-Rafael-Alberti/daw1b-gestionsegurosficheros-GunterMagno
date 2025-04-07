package model

class SeguroAuto : Seguro {

    private var descripcion: String = ""
    private var combustible: String = ""
    private var tipoAuto: TipoAuto = TipoAuto.COCHE
    private var tipoCobertura: Cobertura = Cobertura.TERCEROS
    private var asistenciaCarretera: Boolean = true
    private var numPartes: Int = 0

    constructor(
        dniTitular: String,
        importe: Double,
        descripcion: String,
        combustible: String,
        tipoAuto: TipoAuto,
        cobertura: Cobertura,
        asistenciaCarretera: Boolean,
        numPartes: Int
    ) : super(generarID(), dniTitular, importe) {
        this.descripcion = descripcion
        this.combustible = combustible
        this.tipoAuto = tipoAuto
        this.tipoCobertura = cobertura
        this.asistenciaCarretera = asistenciaCarretera
        this.numPartes = numPartes
    }

    private constructor(
        numPoliza: Int,
        dniTitular: String,
        importe: Double,
        descripcion: String,
        combustible: String,
        tipoAuto: TipoAuto,
        cobertura: Cobertura,
        asistenciaCarretera: Boolean,
        numPartes: Int
    ) : super(numPoliza, dniTitular, importe) {
        this.descripcion = descripcion
        this.combustible = combustible
        this.tipoAuto = tipoAuto
        this.tipoCobertura = cobertura
        this.asistenciaCarretera = asistenciaCarretera
        this.numPartes = numPartes
    }

    private val tipoSeguro = tipoSeguro()
    private val id = generarID()

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        val argumento = if (numPartes > 0) { interes + (2 * numPartes) } else interes
        return importe * (1 + (argumento / 100))
    }

    override fun serializar(separador: String): String {
        return super.serializar(separador) + "$separador$descripcion$separador$combustible$separador$tipoAuto$separador$tipoCobertura$separador$asistenciaCarretera$separador$numPartes$separador$tipoSeguro"
    }

    override fun toString(): String {
        return "Seguro de Auto(NºPoliza: $numPoliza, DNI Titular: ${getDNI()}, Importe: $importe, Descripcion: $descripcion, Combustible: $combustible, Tipo Auto: $tipoAuto, Tipo Cobertura: $tipoCobertura, Asistencia: $asistenciaCarretera, NºPartes: $numPartes)"
    }

    companion object{
        var numPolizasAuto = 399999
        fun generarID(): Int{
            return (numPolizasAuto++)
        }

        fun crearSeguro(datos: List<String>): SeguroAuto {
            require(datos.size == 10) { "Se necesitan exactamente 10 datos para crear SeguroAuto" }
            return SeguroAuto(
                numPoliza = datos[0].toInt(),
                dniTitular = datos[1],
                importe = datos[2].toDouble(),
                descripcion = datos[3],
                combustible = datos[4],
                tipoAuto = TipoAuto.getAuto(datos[5]),
                cobertura = Cobertura.getCobertura(datos[6]),
                asistenciaCarretera = datos[7].toBoolean(),
                numPartes = datos[8].toInt()
            )
        }

        fun nuevoSeguro(
            dniTitular: String,
            importe: Double,
            descripcion: String,
            combustible: String,
            tipoAuto: TipoAuto,
            cobertura: Cobertura,
            asistenciaCarretera: Boolean,
            numPartes: Int
        ): SeguroAuto {
            return SeguroAuto(
                dniTitular = dniTitular,
                importe = importe,
                descripcion = descripcion,
                combustible = combustible,
                tipoAuto = tipoAuto,
                cobertura = cobertura,
                asistenciaCarretera = asistenciaCarretera,
                numPartes = numPartes
            )
        }
    }
}