package model

enum class Cobertura(val desc: String) {
    TERCEROS("Terceros"),
    TERCEROS_AMPLIADO("Terceros ampliado"),
    FRANQUICIA_200("Todo Riesgo con Franquicia de 200$"),
    FRANQUICIA_300("Todo Riesgo con Franquicia de 300$"),
    FRANQUICIA_400("Todo Riesgo con Franquicia de 400$"),
    FRANQUICIA_500("Todo Riesgo con Franquicia de 500$"),
    TODO_RIESGO("Todo Riesgo");

    companion object{
        fun getCobertura(valor: String): Cobertura{
            return when(valor.lowercase()){
                "terceros" -> TERCEROS
                "terceros ampliado" -> TERCEROS_AMPLIADO
                "franquicia 200" -> FRANQUICIA_200
                "franquicia 300" -> FRANQUICIA_300
                "franquicia 400" -> FRANQUICIA_400
                "franquicia 500" -> FRANQUICIA_500
                "todo riesgo" -> TODO_RIESGO
                else -> TERCEROS
            }
        }
    }
}