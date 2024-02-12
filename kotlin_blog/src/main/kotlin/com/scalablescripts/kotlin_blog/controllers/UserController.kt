package com.scalablescripts.kotlin_blog.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController {

    @GetMapping("/hello")
    fun hello(): String{
        return "hello. Spy!"
    }
}