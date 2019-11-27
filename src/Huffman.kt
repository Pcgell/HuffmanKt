import java.util.*
import kotlin.collections.HashMap

fun main(args :Array<String>){

    val originalMessage = "hola mundo y raimundooo"


    val table = HashMap<Char,Int>()
    for (letra in originalMessage){
        if(table.containsKey(letra)){
            table[letra] = table[letra]!! + 1
        }else{
            table[letra] = 1
        }


    }

    val heap = PriorityQueue<HuffPair>()

    for(pair in table){
        heap.add(HuffPair(pair.key,pair.value))
    }

    println(heap)

    while (heap.size > 1){
        val hiz = heap.remove()
        val hder = heap.remove()
        heap.add(HuffPair(hiz,hder))
        println(heap)
    }

    val codes = HashMap<Char,String>()
    heap.peek().depthFirstSearch("",codes)

    println(codes)
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
    for(i in 0 until bitSet.length()){
        print(if(bitSet[i]) 1 else 0)
    }
    println()
    val longArray = bitSet.toByteArray()
    print("[")
    for(i in 0 until longArray.size){
        print(" ${longArray[i]} " )
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