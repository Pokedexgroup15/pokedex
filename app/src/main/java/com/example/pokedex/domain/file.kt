package com.example.pokedex.domain

import java.io.File
import java.io.FileReader
import java.io.FileWriter

class file {
fun initialize(content: String) {
    val fileName = "localdata.txt"
    val contentToWrite = "Hello, this is the content of the file!"

    // Check if the file already exists
    if (!fileExists(fileName)) {
        // Create a file and write content to it
        writeToFile(fileName, contentToWrite)
    } else {
        println("File $fileName already exists. Skipping creation.")
    }

    // Read content from the file
    val readContent = readFromFile(fileName)
    println("Content read from file: $readContent")
}


}



fun fileExists(fileName: String): Boolean {
    val file = File(fileName)
    return file.exists()
}

fun writeToFile(fileName: String, content: String) {
    try {
        // Create a FileWriter object
        val fileWriter = FileWriter(fileName)

        // Write the content to the file
        fileWriter.write(content)

        // Close the FileWriter to release resources
        fileWriter.close()

        println("Content written to $fileName successfully.")
    } catch (e: Exception) {
        println("Error writing to file: ${e.message}")
    }
}

fun readFromFile(fileName: String): String {
    try {
        // Create a FileReader object
        val fileReader = FileReader(fileName)

        // Read content from the file
        val charArray = CharArray(100)
        val readChars = fileReader.read(charArray)

        // Close the FileReader to release resources
        fileReader.close()

        // Convert the read characters to a String
        return String(charArray.copyOfRange(0, readChars))
    } catch (e: Exception) {
        println("Error reading from file: ${e.message}")
        return ""
    }
}


