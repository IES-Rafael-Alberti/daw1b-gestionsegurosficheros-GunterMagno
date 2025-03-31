package model

class SeguroAuto : Seguro {

    private var descripcion: String = ""
    private var combustible: String = ""
    private var tipoAuto: TipoAuto = TipoAuto.COCHE
    private var tipoCobertura: String = ""
    private var asistenciaCarretera: Boolean = true
    private var numPartes: Int = 0

    constructor(dniTitular: String,
                importe: Double,
                descripcion: String,
                combustible: String,
                tipoAuto: TipoAuto,
                cobertura: Cobertura,
                asistenciaCarretera: Boolean,
                numPartes: Int) : super(generarID(),dniTitular,importe)

    private constructor(numPoliza: Int,
                        dniTitular: String,
                        importe: Double,
                        descripcion: String,
                        combustible: String,
                        tipoAuto: TipoAuto,
                        cobertura: Cobertura,
                        asistenciaCarretera: Boolean,
                        numPartes: Int
    ) : super(this.numPoliza,
        this.dniTitular,
        this.importe,
        this.descripcion,
        this.combustible,
        this.tipoAuto,
        this.tipoCobertura,
        this.asistenciaCarretera,
        this.numPartes
        )

    private val tipoSeguro = tipoSeguro()
    private val id = generarID()

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        val argumento = if (numPartes > 0) { interes + (2 * numPartes) } else interes
        return importe * (1 + (argumento / 100))
    }

    override fun serializar(separador: String): String {
        return super.serializar(separador) + "$separador$descripcion$separador$combustible$separador$tipoAuto$separador$tipoCobertura$separador$asistenciaCarretera$separador$numPartes$separador$tipoSeguro"
    }

    companion object{
        var numPolizasAuto = 399999
        fun generarID(): Int{
            return (numPolizasAuto++)
        }

        fun crearSeguro(datos: List<String>): SeguroAuto{
            require(datos.size == 10) { "Datos incorrectos para crear SeguroAuto" }
            return SeguroAuto(
                numPoliza = datos[0].toInt(),
                dniTitular = datos[1],
                id = datos[2].toInt(),
                importe = datos[3].toDouble(),
                descripcion = datos[4],
                combustible = datos[5],
                tipoAuto = TipoAuto.getAuto(datos[6]),
                cobertura = Cobertura.getCobertura(datos[7]),
                asistenciaCarretera = datos[8].toBoolean(),
                numPartes = datos[9].toInt()
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
                numPoliza = ++numPolizasAuto,
                dniTitular = dniTitular,
                id = numPolizasAuto,
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