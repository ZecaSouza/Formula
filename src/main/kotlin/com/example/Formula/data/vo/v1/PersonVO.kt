package com.example.Formula.data.vo.v1

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.github.dozermapper.core.Mapping
import org.springframework.hateoas.RepresentationModel
import java.time.LocalDate

@JsonPropertyOrder("id", "address", "first_Name", "last_Name", "gender", "birthDay")
open class PersonVO(
    @Mapping("id")
    open var key: Long = 0,

    @field: JsonProperty("first_Name")
    open var firstName: String = "",

    @field: JsonProperty("last_Name")
    open var lastName: String = "",

    open var address: String = "",

    @field: JsonIgnore
    open var gender: String = "",

    open var birthDay: LocalDate = LocalDate.now()
) : RepresentationModel<PersonVO>()
