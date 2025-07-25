package com.example.Formula.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND)
class RequiredObjectIsNullException: RuntimeException {
    constructor() : super("It is not allowed to persist a null object!\n")
    constructor(exception: String) : super(exception)
}