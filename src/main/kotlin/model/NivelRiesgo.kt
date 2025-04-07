package model

enum class NivelRiesgo(interesAplicado: Double) {
    BAJO(2.0),
    MEDIO(5.0),
    ALTO(10.0);

    companion object{
        fun getRiesgo(valor :String): NivelRiesgo{
            return when(valor.lowercase().trim()){
                "bajo" -> BAJO
                "medio" -> MEDIO
                "alto" -> ALTO
                else -> BAJO
            }
        }
    }
}