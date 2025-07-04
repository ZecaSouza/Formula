package com.example.Formula.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "person_table")
class Person {

    @Id
    var id: Long = 0

    var firstName: String = ""

    var lastName: String = ""

    var address: String = ""

    var gender: String = ""

    var birthDay: LocalDate = LocalDate.now()
}
