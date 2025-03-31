import app.Consola

fun main(){


   /* val archivo = "Seguros.txt"
    val repo = RepositorioSegurosFicheros(archivo, mapaSeguros)

    // Crear seguros y guardarlos
    val seguroHogar = model.SeguroHogar(1,"12345678A",101,500.0,150000.0,"Calle Mayor, 12",80)
    val seguroAuto = model.SeguroAuto(2,"98765432B",102,"Toyota Corolla Azul","Gasolina","Turismo","Todo Riesgo",true, 1,700)

    repo.guardarSeguro(seguroHogar)
    repo.guardarSeguro(seguroAuto)

    // Cargar seguros desde el fichero
    val segurosCargados = repo.cargarSeguros()
    segurosCargados.forEach { println(it.tipoSeguro() + ": " + it.serializar()) }

*/

    //-----------------------Arriba lo de Diego del readme(mirarselo)--------------------------------//

    val consola = Consola()

    consola.menuPrincipal()

}



//ToDo Importante

//ToDo Como hacer constructores secundarios
//ToDo hacer bien el hashcode de Seguro y el equals
//ToDo Terminar funcion cargarSeguros en RepoSegurosFich


//ToDo Importancia media

//ToDO que es eso de serializar con this::class.memberProperties.jointToString(separador){cosas raras}



//ToDo Menos importancia

//ToDo arreglar/terminar menus
//ToDo el 700 en seguroAuto a que se refiere???
