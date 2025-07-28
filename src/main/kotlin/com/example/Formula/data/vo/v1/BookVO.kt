package com.example.Formula.data.vo.v1

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.hateoas.RepresentationModel
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@JsonPropertyOrder("id", "author", "title", "launch_date", "price")
open class BookVO(

    @field:JsonProperty("id")
    open var key: Long = 0,

    @field:JsonProperty("author")
    open var author: String = "",

    @field:JsonProperty("title")
    open var title: String = "",

    @field:JsonProperty("launch_date")
    open var launchDate: LocalDate = LocalDate.now(),

    @field:JsonProperty("price")
    open var price: BigDecimal = BigDecimal.ZERO

) : RepresentationModel<BookVO>()
