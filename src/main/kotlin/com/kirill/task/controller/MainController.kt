package com.kirill.task.controller

import com.kirill.task.dto.FileRequestDto
import com.kirill.task.service.CountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class MainController {
    @Autowired
    private lateinit var countService: CountService

    @PostMapping("/file")
    fun count(@RequestBody request: FileRequestDto): ResponseEntity<String> {
        return try {
            val count = countService.countUniqueIpFromFile(request.file)
            ResponseEntity.ok().body(count.toString())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.toString())
        }
    }
}