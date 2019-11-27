import java.util.*
import kotlin.collections.HashMap


private fun pressAnyKeyToContinue() {
    println("presione Enter para continuar...")
    try {
        System.`in`.read()
    } catch (e: Exception) {
    }

}

fun main(args :Array<String>){

    println("para comprimir un mensaje utilizando huffman se siguen los siguientes pasos.")

    pressAnyKeyToContinue()
    val originalMessage = "hola mundo y raimundooo"

    println("mensaje a comprimir")
    println(originalMessage)


    println("1. primero se calcula una tabla de fecuencia  ")

    val table = HashMap<Char,Int>()
    for (letra in originalMessage){
        if(table.containsKey(letra)){
            table[letra] = table[letra]!! + 1
        }else{
            table[letra] = 1
        }


    }

    println("tabla de frequencia ")
    println(table)

    println()
    println("2. se crea un nodoHuffman y se ingresan a una cola de priodidad apartir de la tabla de frecuencia")
    pressAnyKeyToContinue()

    val heap = PriorityQueue<HuffPair>()

    for(pair in table){
        heap.add(HuffPair(pair.key,pair.value))
    }

    println(heap)


    println("3. se construye el arbol de abajo hacia arriba utilizando la cola de prioridad")
    pressAnyKeyToContinue()


    while (heap.size > 1){
        val hiz = heap.remove()
        val hder = heap.remove()
        heap.add(HuffPair(hiz,hder))
        println(heap)
    }

    println("4. al finalizar se obtiene un solo nodo en la tabla de prioridad este nodo es la raiz del arbol Huffman\n para calcular los codigos hay que hacer un recorido en profundidad ")
    pressAnyKeyToContinue()
    val codes = HashMap<Char,String>()
    heap.peek().depthFirstSearch("",codes)

    println(codes)

    println("5. una vez calculdo los codigos se puede ir letra por letra encodificado el mensaje final ")
    pressAnyKeyToContinue()
    var index = 0
    val bitSet= BitSet()
    for (letter in originalMessage) {
        val code = codes[letter]
        for(bit in code!!){
            if(bit == '1'){
                bitSet[index] = true;
            }else{
                bitSet[index] = false;
            }
            index++
        }
    }

    println("mesage length ${bitSet.length()} mesage size ${bitSet.size()}")

    println(" representacion binaria del mensaje compresso")
    pressAnyKeyToContinue()
    for(i in 0 until bitSet.length()){
        print(if(bitSet[i]) 1 else 0)
    }
    println()

    println(" representacion como arreglos de byte del mensaje compresso")
    pressAnyKeyToContinue()
    val byteArray = bitSet.toByteArray()
    print("[")
    for(i in 0 until byteArray.size){
        print(" ${byteArray[i]} " )
    }
    println("]")

    println(" representacion como arreglos de Characteres del mensaje compresso")
    pressAnyKeyToContinue()
    print("[")
    for(i in 0 until byteArray.size){
        print("${byteArray[i].toChar()}" )
    }
    println("]")

}


class HuffPair : Comparable<HuffPair>{
    override fun compareTo(other: HuffPair): Int {
       if(this.freq > other.freq){
           return 1
       }
        if(this.freq == other.freq){
            return 0
        }
        return -1

    }

    val letter: Char
    var freq : Int

    val hijoIz: HuffPair?
    val hijoDer:HuffPair?

    constructor(letter:Char,freq: Int){
        this.freq = freq
        this.letter = letter
        hijoDer = null
        hijoIz = null
    }

    constructor(hijoIz: HuffPair, hijoDer: HuffPair ){
        letter = '@'
        freq = hijoIz.freq + hijoDer.freq
        this.hijoDer = hijoDer
        this.hijoIz = hijoIz
    }

    override fun toString(): String {
        return "$letter => $freq"
    }

    fun depthFirstSearch(curCode :String , codes:HashMap<Char,String>){
        if(null != hijoIz) hijoIz.depthFirstSearch(curCode+"1",codes)
        if(hijoDer == hijoIz){
            //println("$letter => $curCode")
            codes[letter] = curCode
        }
        if (null != hijoDer) hijoDer.depthFirstSearch(curCode +"0",codes)
    }


}