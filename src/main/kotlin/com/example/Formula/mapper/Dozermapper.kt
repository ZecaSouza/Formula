package com.example.Formula.mapper

import org.modelmapper.ModelMapper

object DozerMapper {

    private val mapper : ModelMapper = ModelMapper()

    fun <O, D> passerObject(origem: O, destination: Class<D>?): D {
        return mapper.map(origem, destination)
    }

    fun <O, D> passerObjectList(origem: List<O>, destination: Class<D>?): List<D> {
        return origem.map { mapper.map(it, destination) }
    }

}