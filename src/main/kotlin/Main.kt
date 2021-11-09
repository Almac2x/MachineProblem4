import java.io.File
import java.io.InputStream
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
                check()
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

fun check() {

    val directory = File("files//") // gets directory
    val numberOfBlocks = directory.list().size //size of the directory
    val hashMapOfBlocks = mutableMapOf<String, String>() // hash of blocks inside the txt blocks <FileName, Hash>
    var listFiles = directory.listFiles() // list of files in the directory
    var listFilesMap = mutableMapOf<String,File>() // a map of files

    var justHashFiles = mutableMapOf<String,String>() // a map of the files corresponding hashes

    // Declare our Hasher
    val shaDigest = MessageDigest.getInstance("SHA-256")
    val hash256 : Hash256 = Hash256()


    for (file in listFiles) {
        //gets the hashes inside the txt blocks
        hashMapOfBlocks[file.name] = file.readLines().last() // gets the last line of the txt file which are the hash of the previous block
        //hashes all the txt blocks again to compare the contents of the txt blocks
        justHashFiles[file.name] = hash256.getFileChecksum(shaDigest,File("files//${file.name}")).toString()

       listFilesMap[file.name] = file //adds the files into a hashmap
    }

    // check if all the blocks are chained together to know if one is tampered

    //gets the hash of each txt block inside
    //Start of tampering here

    println("Block Statuses:")

    val accountedBlocks = mutableMapOf<String,File>()
    //check if all blocks are accounted for
    for(i in 0 until numberOfBlocks){
        val blockName = "block${i}"
        if(hashMapOfBlocks[blockName] != null){ // checks if the block is in order and name did not change
            accountedBlocks[blockName] = listFilesMap[blockName] as File
        }
        else
            break // it will break if the iteration of the blocks is inconsistent
    }


    val blockStatuses = mutableMapOf<String,String>()
    // countdown to zero
    //checks if each block hash value has a chain to
    for (i in hashMapOfBlocks.size-1 downTo  0 ){
        if (i == 0)
            break

        if( i == hashMapOfBlocks.size-1){
            if(hashMapOfBlocks["trailingBlock"] == justHashFiles["block${i-1}"])
                blockStatuses["block${i-1}"] = "Ok"
            else
                blockStatuses["block${i-1}"] = "Tampered"

        }else {
            if(hashMapOfBlocks["block${i}"] == justHashFiles["block${i-1}"]){
                blockStatuses["block${i-1}"] = "Ok"
            }
            else
                blockStatuses["block${i-1}"] = "Tampered"
        }
    }

    //print statuses
    blockStatuses.forEach(){
        println("${it.key} - ${it.value}")
    }

 //end of checking tampering here

println("\nAccount Balances")

    //Start of Computing
        //Initialize Input Reader
    val inputReader = InputReader()

    val accounts = mutableMapOf<String,Double>()
    val genesisFile = accountedBlocks["block0"] as File

    //get accounts and its values and assign it into accounts map

    genesisFile.readLines().forEach(){
        val inputRead = inputReader.readInput(it)
        accounts[inputRead[0]] = inputRead[1].toDouble()
    }

    for (file in accountedBlocks){ // mutableMap is in the order that I inserted the values

        if(file.key == "block0") // skips block 0 since it is just inserting the files
            continue
        else{ // starts from block 1

            val transactions = file.value.readLines().toMutableList()
            transactions.remove(transactions.last())

            transactions.forEach(){
                println(it)
            }

        }

    }




    println()
} // end of Check



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

    //create trailing block for hash of the last transaction block
    val shaDigest = MessageDigest.getInstance("SHA-256")
    // Declare our Hasher
    val hash256 : Hash256 = Hash256()

    File("files//trailingBlock").printWriter().use { out ->
        out.println(hash256.getFileChecksum(shaDigest,File("files//block${iterator-1}"))) // 4-1 = 3
    }

    println("Encoding End")
}

fun createBlockFile(blockNumber : Int, transactions : MutableList<String> ){

    val shaDigest = MessageDigest.getInstance("SHA-256")
    // Declare our Hasher
    val hash256 : Hash256 = Hash256()

    val blockName ="block${blockNumber}" // concatenates blockNumber to blockName
    File("files//${blockName}").printWriter().use { out ->

        for (i in 0 until transactions.size)

            if (i == transactions.size - 1) {

                out.println(
                    transactions[i].uppercase() + "\n" + hash256.getFileChecksum(
                        shaDigest,
                        File("files//block${blockNumber - 1}")
                    )
                ) //write the hash of the previous block
            } else {
                out.println(transactions[i].uppercase())
            }

//        transactions.forEach {
//            out.println("${it.uppercase()}")
//        }

    }
}
