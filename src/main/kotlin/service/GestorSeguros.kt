package service

import model.TipoAuto
import model.Cobertura
import model.NivelRiesgo
import model.Seguro
import model.SeguroHogar
import model.SeguroAuto
import model.SeguroVida
import java.time.LocalDate
import data.IRepoSeguros

class GestorSeguros(private val repoSeguros: IRepoSeguros
): IServSeguros {
    override fun contratarSeguroHogar(
        dniTitular: String,
        importe: Double,
        metrosCuadrados: Int,
        valorContenido: Double,
        direccion: String,
        anioConstruccion: Int
    ): Boolean {
        val seguro = SeguroHogar.nuevoSeguro(dniTitular,importe,metrosCuadrados,valorContenido,direccion,anioConstruccion)

        return repoSeguros.agregar(seguro)
    }

    override fun contratarSeguroAuto(
        dniTitular: String,
        importe: Double,
        descripcion: String,
        combustible: String,
        tipoAuto: TipoAuto,
        cobertura: Cobertura,
        asistenciaCarretera: Boolean,
        numPartes: Int
    ): Boolean {
        val seguro = SeguroAuto.nuevoSeguro(dniTitular,importe,descripcion,combustible,tipoAuto,cobertura,asistenciaCarretera,numPartes)

        return repoSeguros.agregar(seguro)
    }

    override fun contratarSeguroVida(
        dniTitular: String,
        importe: Double,
        fechaNacimiento: LocalDate,
        nivelRiesgo: NivelRiesgo,
        indemnizacion: Double
    ): Boolean {
        val seguro = SeguroVida.nuevoSeguro(dniTitular,importe,fechaNacimiento,nivelRiesgo,indemnizacion)

        return repoSeguros.agregar(seguro)
    }

    override fun eliminarSeguro(numPoliza: Int): Boolean {
        return repoSeguros.eliminar(numPoliza)
    }

    override fun consultarTodos(): List<Seguro> {
        return repoSeguros.obtenerTodos()
    }

    override fun consultarPorTipo(tipoSeguro: String): List<Seguro> {
        return repoSeguros.obtener(tipoSeguro)
    }
}