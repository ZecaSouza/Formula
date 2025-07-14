package com.example.Formula.exceptions.handler

import com.example.Formula.exceptions.RequiredObjectIsNullException
import com.example.Formula.exceptions.ResourceNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.Date

data class ExceptionResponse(
    val timestamp: Date,
    val message: String?,
    val details: String
)

@ControllerAdvice
class CustomizedRespondeEntityExceptionHandler : ResponseEntityExceptionHandler() {

    // Handle specific ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(
        ex: ResourceNotFoundException,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errorDetails = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors.joinToString("; ") { error: FieldError ->
            "${error.field}: ${error.defaultMessage}"
        }
        val errorDetails = ExceptionResponse(
            Date(),
            "Validation Failed",
            errors
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errorDetails = ExceptionResponse(
            Date(),
            "Malformed JSON request",
            ex.message ?: ""
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errorDetails = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(RequiredObjectIsNullException::class)
    fun handleBadRequestException(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errorDetails = ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }
}