import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.security.MessageDigest
import kotlin.experimental.and

class Hash256() {

    @Throws(IOException::class)
    fun getFileChecksum(digest: MessageDigest, file: File): String? {
        //Get file input stream for reading the file content
        val fis = FileInputStream(file)

        //Create byte array to read data in chunks
        val byteArray = ByteArray(1024)
        var bytesCount = 0

        //Read file data and update in message digest
        while (fis.read(byteArray).also { bytesCount = it } != -1) {
            digest.update(byteArray, 0, bytesCount)
        }

        //close the stream; We don't need it now.
        fis.close()

        //Get the hash's bytes
        val bytes: ByteArray = digest.digest()

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        val sb = StringBuilder()
        for (i in bytes.indices) {
            sb.append(((bytes[i] and 0xff.toByte()) + 0x100).toString(16).substring(1))
        }

        //return complete hash
        return sb.toString()
    }


}