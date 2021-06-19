fun main() {
    println("Hola mundo")


    val estadoCivilWhen: String = "S"
    when (estadoCivilWhen) {
        ("S") -> {
            println("Acercarse")
        }
        "C" -> {
            println("Alejarse")
        }
        "UN" -> println("hablar")
        else -> println("No reconocido")
    }

    val coqueteo = if (estadoCivilWhen == "S") "SI" else "NO"
    imprimirNombre("Adrian")
    calcularSueldo(100.00)
    calcularSueldo(100.00, 14.00, 25.00)

    //PARAMETROS NOMBRADOS puedo mover el orden de los parametros
    calcularSueldo(
        bonoEspecial = 15.00,
        //tasa=12.00 se sobreentiende
        sueldo = 150.00,

        )
    calcularSueldo(
        tasa = 14.00,
        bonoEspecial = 30.00,
        sueldo = 1000.00

    )

    //TIPOS DE ARREGLOS
    //Tenemos arreglos estaticos y dinamicos
    //  Arreglos est[aticos, no podemos modificar los valores que estan dentro
// del arreglo
    val arregloEstatico: Array<Int> = arrayOf(1, 2, 3)
    // LOs arreglos dinamicos nos permiten utilizar otras funcionalidades interesantes
    // como aniadir mas elementos
    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //OPERADORES sirven tanto para los arreglos dinamicos y estaticos
    //FOR EACH, UNIT NO DEVUELVE NADA, NOS AYUDA A ITERAR UN ARREGLO

    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor actual: ${valorActual}") //no es necesario concatenar

        }
    println(respuestaForEach)

    //A veces es necesario conocer el indice de algun elemento
    //it:INt es el valor o valores que van a llegar a esta funcion
    //si solamente se recibe un parametro este se va allmar it

    arregloDinamico
        .forEachIndexed { indice: Int, valorActual: Int ->
            println("Valor ${ valorActual } Indice : ${ indice }")
        }


    //Operador MAP, nos permite cambiar el arreglo, el map devuelve un
    //arreglo mutado, un entero y podemos devolver un doble
    val respuestaMap: List<Double> =arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble()+100.00
        }
    val respuestaDos=arregloDinamico.map {it+15}
    println(respuestaMap)

   //Operador Filter, nos devuelve una expresion verdadera o falsa
    //devuelve items del arreglo que cumplen con el arreglo

    val respuestFilter: List<Int> = arregloDinamico
    .filter {valorActual:Int ->
        val mayoresaCinco:Boolean=valorActual>5 //expresion o condicion
        return@filter mayoresaCinco
    }

    val respuestFilterDos= arregloDinamico.filter { it<=5 }
    println(respuestFilter)
    println(respuestFilterDos)

    val respuestaAll: Boolean=arregloDinamico
        .all{valorActual:Int->
            return@all (valorActual>5)
        }
    println(respuestaAll)

    val respuestaAny: Boolean=arregloDinamico
        .any{valorActual:Int->
            return@any (valorActual>5)
        }
    println(respuestaAny)

    val respuestaReduce: Int=arregloDinamico
        .reduce{
            acumulado:Int, valorActual:Int->
            return@reduce(acumulado+valorActual)
        }
    println(respuestaReduce)

    val arregloDanio= arrayListOf<Int>(12,15,8,10)
    val respuestaReduceFold= arregloDanio
        .fold(
            100,
            {
                acumulado, valorActualIteracion->
                return@fold acumulado-valorActualIteracion
            }
        )
    println(respuestaReduceFold)

    val vidaActual: Double=arregloDinamico
        .map{it*2.3}
        .filter { it>20 }
        .fold(100.00,{acc,i->acc-i})
        .also{println(it)}
    println("Valor vida actual ${vidaActual}")



    val ejemploUno=Suma(1,2)
    val ejemploDos=Suma(null,2)
    val ejemploTres=Suma(1,null)

    println(ejemploUno.sumar())
    println(Suma.historialSumas)
    println(ejemploDos.sumar())
    println(Suma.historialSumas)
    println(ejemploTres.sumar())
    println(Suma.historialSumas)




}// FIN BLOQUE MAINkk

//Tipo nombre= valor

val nombreProfesor: String = "Adrian Eguez"
val sueldo: Double = 1.2
val estadoCivil: Char = 'S'
//val fechaNacimiento: Date= Date()

//CONDICIONALES

////if(true){
//verdadero
//}  else{
//falso
//}
// Cuando no devuelve nada se usa el UNIT
fun imprimirNombre(nombre: String): Unit {
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional
    bonoEspecial: Double? = null // puede mandarme como no pueden mandarme
    //bonoEspecial: Double? ///es lo mismo
// cuando vemos una variable con el signo de interrogacion quiere decir que
//la variable puede ser nula
//Para identar el c[odigo usamos control+a y control alt+l
): Double {
    //String->String?
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) + bonoEspecial
    }

}

abstract class NumeroJoava{
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int,   //Parametros Requeridos
        dos: Int
    ){
        numeroUno=uno
        numeroDos=dos
        println("Inicializar")
    }
}


abstract class Numeros(// Constructor Primario
protected var numeroUno: Int,
protected var numeroDos: Int){
    init{
        println("Inicializar")
    }
}
//instancia.numeroUno
//instancia.numeroDos


class Suma(
    uno: Int,  //parametro requerido
    dos: Int //parametro requerido
): Numeros(uno, dos){ //constructor papa (super)
    init {
        this.numeroUno
        this.numeroDos
    }

    constructor(
        uno: Int?,
        dos: Int
    ):this(
        if (uno==null)0 else uno,
        dos
    )

    constructor(
        uno: Int,
        dos: Int?
    ):this(
    uno,
    if(dos==null)0 else dos
    )


    //FUNCIONES
    public fun sumar():Int{
        //val total: Int=this.numeroUno+this.numeroDos
        val total: Int= numeroUno+numeroDos
        agregarHistorial(total)
        return total
    }

    companion object{
        val historialSumas= arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma: Int){
            historialSumas.add(valorNuevaSuma)
            println(historialSumas)
        }
    }
}


