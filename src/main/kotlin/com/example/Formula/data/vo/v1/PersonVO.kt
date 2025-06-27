package com.example.Formula.data.vo.v1

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDate

@JsonPropertyOrder("id", "address", "first_Name", "last_Name", "gender", "birthDay")
data class PersonVO(

    var id: Long = 0,

    @field: JsonProperty("first_Name")
    var firstName: String = "",

    @field: JsonProperty("last_Name")
    var lastName: String = "",

    var address: String = "",

    @field: JsonIgnore
    var gender: String = "",

    var birthDay: LocalDate = LocalDate.now()
)
