fun main(args: Array<String>) {

    // Declare our Hasher
    val hash256 : Hash256 = Hash256()

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

    var reader : String?
    val accounts : MutableList<Account> = mutableListOf()
    val inputReader : InputReader = InputReader()
    var currentBlock : Block? = null

    do{
        if(currentBlock == null){
             currentBlock = Block() // create here the genesis Block
            println("Genesis Block Created")
        }

        reader = readLine().toString().lowercase()


    }while(reader != "q")

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