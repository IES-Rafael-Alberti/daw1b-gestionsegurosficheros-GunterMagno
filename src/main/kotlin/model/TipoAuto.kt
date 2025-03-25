package model

enum class TipoAuto {
    COCHE,MOTO,CAMION;

    companion object{
        fun getAuto(valor: String): TipoAuto{
            return when(valor.lowercase().trim()){
                "coche" -> COCHE
                "moto" -> MOTO
                "camion" -> CAMION
                else -> COCHE
            }
        }
    }
}