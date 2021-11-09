import java.io.File
import java.security.MessageDigest

fun main(args: Array<String>) {


    readInput()
}

fun readInput() : HashMap<String,String>{

    var reader : String? = null
    do{

        print("Input E -> Encode\t|\tC -> Check\t|\tQ -> Quit: ")
        reader = readLine().toString().lowercase()

        // Loop for initial Input (e or q) e -> For Encoding , q -> End
        when(reader){

            "e"-> {
                 encode()
            }

            "c"->{
                println("End")
            }
            "q" ->{
                println("GoodBye")
            }
            else -> println("Input not Valid")

        }

    }while(reader != "q")

    return HashMap<String,String>()
}

fun encode() {

    println("Type Ok to end each transaction ")

    var reader : String? = null
    val accounts : MutableList<Account> = mutableListOf()
    val transactions : MutableList<String> = mutableListOf()

    val inputReader : InputReader = InputReader()
    var currentBlock : Block? = null
    var iterator : Int = 1 // we start at 1

    do{
        if(currentBlock == null){
             currentBlock = Block() // create here the genesis Block
            println("Genesis Block Created")
            continue // if current block is null it will not use the other codes in this do while
        }

        reader = readLine().toString().lowercase()

        if(reader =="ok"){

            createBlockFile(blockNumber = iterator, transactions = transactions) // writes block file
            transactions.clear() //delete all contents in accounts variable
            iterator++

            // make the current block to the new current block <- Di ko alam kung kailangan pa ba ito
        }else
            transactions.add(reader)

        //val readInput = inputReader.readInput(reader) // Index[0] = Sender Index [1] = Receiver Index [2] = Amount Send


    }while(reader != "q")


    println("Encoding End")
}

fun createBlockFile(blockNumber : Int, transactions : MutableList<String> ){

    val shaDigest = MessageDigest.getInstance("SHA-256")
    // Declare our Hasher
    val hash256 : Hash256 = Hash256()

    val blockName ="block${blockNumber}" // concatenates blockNumber to blockName
    File("files//${blockName}").printWriter().use { out ->

        for(i in 0 until transactions.size)

            if(i == transactions.size-1){

                out.println(transactions[i].uppercase()+"\n"+ hash256.getFileChecksum(shaDigest, File("files//block${blockNumber-1}"))) //write the hash of the previous block
            }else{
                out.println(transactions[i].uppercase())
            }

//        transactions.forEach {
//            out.println("${it.uppercase()}")
//        }
    }
}




// Dito Muna
//
////Create checksum for this file
//val file = File("files//testOut.txt")
//
////Use SHA-1 algorithm
//val shaDigest = MessageDigest.getInstance("SHA-256")
//
////SHA-1 checksum
//val shaChecksum: String? = hash256.getFileChecksum(shaDigest, file)
//println(shaChecksum)