package data
import utils.IUtilFicheros
import model.Seguro
import model.SeguroHogar
import model.SeguroAuto
import model.SeguroVida

class RepoSegurosFich(
    private val rutaArchivo: String,
    private val fich: IUtilFicheros
) : RepoSegurosMem(), ICargarSegurosIniciales, IRepoSeguros {

    override fun agregar(seguro: Seguro): Boolean {
        if (fich.agregarLinea(rutaArchivo, seguro.serializar())) {
            return super.agregar(seguro)
        }
        return false
    }

    override fun eliminar(seguro: Seguro): Boolean {
        val segurosActualizados = obtenerTodos().filter { it != seguro }
        if (super.eliminar(seguro) && fich.escribirArchivo(rutaArchivo, segurosActualizados)) {
            return true
        }
        return false
    }

    override fun cargarSeguros(mapa: Map<String, (List<String>) -> Seguro>): Boolean {
        val lineas = fich.leerArchivo(rutaArchivo)
        if (lineas.isNotEmpty()) {
            lineas.forEach { linea ->
                if (linea.isNotBlank()) {
                    val datos = linea.split(";")
                    val tipoSeguro = datos.last()

                    when(tipoSeguro){
                        "Seguro de Hogar" -> SeguroHogar.crearSeguro(datos)
                        "Seguro de Auto" -> SeguroAuto.crearSeguro(datos)
                        "Seguro de Vida" -> SeguroVida.crearSeguro(datos)

                    }
                }
            }
            actualizarContadores(obtenerTodos())
            return true
        }
        return false
    }

    private fun actualizarContadores(seguros: List<Seguro>) {
        val maxHogar = seguros.filter { it.tipoSeguro() == "Seguro de Hogar" }.maxOfOrNull { it.numPoliza }
        val maxAuto = seguros.filter { it.tipoSeguro() == "Seguro de Auto" }.maxOfOrNull { it.numPoliza }
        val maxVida = seguros.filter { it.tipoSeguro() == "Seguro de Vida" }.maxOfOrNull { it.numPoliza }

        if (maxHogar != null) SeguroHogar.numPolizasHogar = maxHogar
        if (maxAuto != null) SeguroAuto.numPolizasAuto = maxAuto
        if (maxVida != null) SeguroVida.numPolizasVida = maxVida
    }
}