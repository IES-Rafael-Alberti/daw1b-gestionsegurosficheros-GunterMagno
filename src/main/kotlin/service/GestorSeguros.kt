package service

import model.*
import java.time.LocalDate

class GestorSeguros: IServSeguros {
    override fun contratarSeguroHogar(
        dniTitular: String,
        importe: Double,
        metrosCuadrados: Int,
        valorContenido: Double,
        direccion: String,
        anioConstruccion: Int
    ): Boolean {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override fun contratarSeguroVida(
        dniTitular: String,
        importe: Double,
        fechaNacimiento: LocalDate,
        nivelRiesgo: NivelRiesgo,
        indemnizacion: Double
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun eliminarSeguro(numPoliza: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun consultarTodos(): List<Seguro> {
        TODO("Not yet implemented")
    }

    override fun consultarPorTipo(tipoSeguro: String): List<Seguro> {
        TODO("Not yet implemented")
    }
}