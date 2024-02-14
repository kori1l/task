package com.kirill.task.service

import com.kirill.task.LongBitSet
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.*


@Service
class CountService {
    companion object {
        const val maxCount = 4_294_967_295L
        const val ipDelimiter = "."
    }
    @Throws(IOException::class)
    fun countUniqueIpFromFile(filePath: String): Int {
        val bitSet = LongBitSet(maxCount)
        var countUniqueIp = 0
        BufferedReader(FileReader(filePath)).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val hash = ipToLongHash(line!!)
                if (!bitSet[hash]) {
                    bitSet.set(hash)
                    countUniqueIp++
                }
            }
        }
        return countUniqueIp
    }

    private fun ipToLongHash(ipAddress: String): Long {
        val parts = ipAddress.split(ipDelimiter)
        require(parts.size == 4) { "Invalid IPv4 address: $ipAddress" }

        var result: Long = 0
        for (i in 0 until 4) {
            val octet = parts[i].toLong()
            require(octet in 0..255) { "Invalid octet in IPv4 address: $ipAddress" }

            result = (result shl 8) or octet
        }

        return result
    }
}