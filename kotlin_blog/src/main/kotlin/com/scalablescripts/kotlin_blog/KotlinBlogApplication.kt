package com.scalablescripts.kotlin_blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinBlogApplication

fun main(args: Array<String>) {
	runApplication<KotlinBlogApplication>(*args)
}
