package com.example.Formula.mapper

import com.example.Formula.data.vo.v1.PersonVO
import com.example.Formula.model.Person
import org.modelmapper.ModelMapper
import org.modelmapper.PropertyMap

object ModelMapperWrapper {

    private val mapper: ModelMapper = ModelMapper().apply {
        // Mapeamento explicito: Person.id <-> PersonVO.key
        addMappings(object : PropertyMap<Person, PersonVO>() {
            override fun configure() {
                map().key = source.id
            }
        })
        addMappings(object : PropertyMap<PersonVO, Person>() {
            override fun configure() {
                map().id = source.key
            }
        })
    }

    fun <O, D> map(origem: O, destination: Class<D>): D {
        return mapper.map(origem, destination)
    }

    fun <O, D> mapList(origem: List<O>, destination: Class<D>): List<D> {
        return origem.map { mapper.map(it, destination) }
    }
}
