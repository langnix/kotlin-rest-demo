package de.harm.kotlin.restdemo

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestDemoApplication

fun main(args: Array<String>) {
    runApplication<RestDemoApplication>(*args) {
        setBannerMode(Banner.Mode.CONSOLE)
    }
}
