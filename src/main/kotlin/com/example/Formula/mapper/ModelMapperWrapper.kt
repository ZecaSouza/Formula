package com.example.Formula.mapper

import com.example.Formula.data.vo.v1.BookVO
import com.example.Formula.data.vo.v1.PersonVO
import com.example.Formula.model.Book
import com.example.Formula.model.Person
import org.modelmapper.ModelMapper
import org.modelmapper.PropertyMap

object ModelMapperWrapper {

    private val mapper: ModelMapper = ModelMapper().apply {

        // Person → PersonVO
        addMappings(object : PropertyMap<Person, PersonVO>() {
            override fun configure() {
                map().key = source.id
            }
        })

        // PersonVO → Person
        addMappings(object : PropertyMap<PersonVO, Person>() {
            override fun configure() {
                map().id = source.key
            }
        })

        // Book → BookVO
        addMappings(object : PropertyMap<Book, BookVO>() {
            override fun configure() {
                map().key = source.id
            }
        })

        // BookVO → Book
        addMappings(object : PropertyMap<BookVO, Book>() {
            override fun configure() {
                map().id = source.key
            }
        })
    }

    fun <O, D> map(origem: O, destino: Class<D>): D {
        return mapper.map(origem, destino)
    }

    fun <O, D> mapList(origem: List<O>, destino: Class<D>): List<D> {
        return origem.map { mapper.map(it, destino) }
    }
}