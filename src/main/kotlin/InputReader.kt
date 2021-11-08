import kotlin.time.measureTime

class InputReader{

    val depositPattern  = "->".toRegex()
    val addPattern =","


    fun readInput( input : String) : List<String>{


        val line = input.replace("\\s".toRegex(),"") // removes the whitespaces

        return when(depositPattern.containsMatchIn(line)){

            true ->{
                var list = line.split(depositPattern) //
                list = list+list.last().split(addPattern)
                return list
            } // for transfer pattern

            false -> {
                line.split(addPattern)
            }

        }

    }



}