package model

enum class Perfil {
    ADMIN,GESTION,CONSULTA;

    companion object{
        fun getPerfil(valor: String): Perfil{
            return when(valor.lowercase().trim()){
                "admin" -> ADMIN
                "gestion" -> GESTION
                "consulta" -> CONSULTA
                else -> {CONSULTA}
            }
        }
    }
}
