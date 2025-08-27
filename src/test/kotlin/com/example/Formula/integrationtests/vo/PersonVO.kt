package com.example.Formula.integrationtests.vo

import java.time.LocalDate

open class PersonVO(

    open var key: Long? = 0,
    open var firstName: String = "",
    open var lastName: String = "",
    open var address: String = "",
    open var gender: String = "",
    open var birthDay: LocalDate = LocalDate.now()
)
