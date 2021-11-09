import kotlin.time.measureTime

class InputReader{

    val depositPattern  = "->".toRegex()
    val addPattern =","


    fun readInput( input : String) : List<String>{


        val line = input.replace("\\s".toRegex(),"") // removes the whitespaces

        return when(depositPattern.containsMatchIn(line)){

            true ->{
                var list = line.split(depositPattern).toMutableList() //Gets the sender
                var tempLast = list.last()
                list.removeLast() // remove last string

                list = (list + tempLast.split(addPattern).toMutableList()) as MutableList<String> // gets the recipient
                return list
            } // for transfer pattern

            false -> {
                line.split(addPattern)
            }

        }

    }



}