package com.kirill.task.service

import com.kirill.task.LongBitSet
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Path

@ExtendWith(SpringExtension::class)
@SpringBootTest
class CountServiceTest {

    @Autowired
    private lateinit var countService: CountService

    @Test
    fun testCountUniqueIpFromFile(@TempDir tempDir: Path) {
        val filePath = createTestFile(tempDir, listOf(
            "0.0.0.0", "255.255.255.255", "0.255.0.0", "192.168.0.3",
            "1.1.1.1", "255.255.255.255", "192.168.0.1", "192.168.0.3"
        ))
        val longBitSet = LongBitSet(4_294_967_295L)
        assertEquals(longBitSet[0],false)

        val result = countService.countUniqueIpFromFile(filePath)
        assertEquals(6, result)
    }

    @Test
    fun testCountUniqueIpFromFileEmptyFile(@TempDir tempDir: Path) {
        val filePath = createTestFile(tempDir, emptyList())

        val result = countService.countUniqueIpFromFile(filePath)
        assertEquals(0, result)
    }

    private fun createTestFile(directory: Path, lines: List<String>): String {
        val filePath = directory.resolve("testFile.txt").toAbsolutePath().toString()
        val file = File(filePath)
        FileWriter(file).use { writer ->
            lines.forEach { line -> writer.write("$line\n") }
        }
        return filePath
    }
}