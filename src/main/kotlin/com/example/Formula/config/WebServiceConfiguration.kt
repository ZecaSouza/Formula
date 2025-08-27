package com.example.Formula.config

import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

class WebServiceConfiguration {

    fun addCorsConfig(): WebMvcConfigurer{
        return object : WebMvcConfigurer{
            override fun addCorsMappings(registry: CorsRegistry) {
                super.addCorsMappings(registry)
            }
        }
    }

}