
class Block {

    lateinit var accounts : HashMap<String,Account>
    val hash : String? = null
    val previousHash: String? = null


    // create genesisBlock
    constructor() {
        println("Genesis Block")
        var input : String
        val inputReader = InputReader()
        this.accounts = hashMapOf()
        var readInputTemp: List<String>
        do{//Adding account to genesis block

            input = readLine().toString().lowercase()

            if( input == "ok") break // breaks the loop if the user inputs ok

            readInputTemp = inputReader.readInput(input)
            accounts[readInputTemp.first()] = Account(accountName = readInputTemp.first(),funds = readInputTemp.last().toDouble())

        }while (input != "ok")

    }

    fun createBlockFile(){

    }


}