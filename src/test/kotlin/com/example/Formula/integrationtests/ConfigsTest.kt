package com.example.Formula.integrationtests

object ConfigsTest {
    const val SERVER_PORT = 8080

    // Headers
    const val HEADER_ORIGIN = "Origin"
    const val HEADER_CONTENT_TYPE = "Content-Type"
    const val HEADER_ACCEPT = "Accept"

    // Content types comuns
    const val CONTENT_TYPE_JSON = "application/json"
    const val CONTENT_TYPE_XML = "application/xml"
    const val CONTENT_TYPE_YAML = "application/x-yaml"
    const val CONTENT_TYPE_TEXT = "text/plain"
    const val CONTENT_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded"
    const val CONTENT_TYPE_MULTIPART = "multipart/form-data"

    // Accepts (para testes de APIs que retornam diferentes formatos)
    const val ACCEPT_JSON = "application/json"
    const val ACCEPT_XML = "application/xml"
    const val ACCEPT_YAML = "application/x-yaml"
    const val ACCEPT_ALL = "*/*"

    const val ORIGIN_LOCALHOST = "http://localhost:8080"
}
