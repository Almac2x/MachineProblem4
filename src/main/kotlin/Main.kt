import java.io.File
import java.security.MessageDigest

fun main(args: Array<String>) {

    // Declare our Hasher
    val hash256 : Hash256 = Hash256()

    val userInput = readLine().toString()

    println(userInput)





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