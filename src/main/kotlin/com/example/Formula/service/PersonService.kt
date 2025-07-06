package com.example.Formula.service

import com.example.Formula.controller.PersonController
import com.example.Formula.data.vo.v1.PersonVO
import com.example.Formula.mapper.ModelMapperWrapper
import com.example.Formula.model.Person
import com.example.Formula.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.logging.Logger

@Service
class PersonService(
    @Autowired private val repository: PersonRepository,
) {

    private val logger: Logger = Logger.getLogger(PersonService::class.java.name)

    fun findById(id: Long): PersonVO {
        logger.info("Finding one person with ID $id")
        val person = repository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Person with id $id not found")
        }
        val personVO: PersonVO = ModelMapperWrapper.map(person, PersonVO::class.java)
        addHateoasLinks(personVO)
        return personVO
    }

    fun create(personVO: PersonVO): PersonVO {
        logger.info("Creating person: ${personVO.firstName} ${personVO.lastName}")
        val entity: Person = ModelMapperWrapper.map(personVO, Person::class.java)
        val saved = repository.save(entity)
        val personVOResult: PersonVO = ModelMapperWrapper.map(saved, PersonVO::class.java)
        addHateoasLinks(personVOResult)
        return personVOResult
    }

    fun updateById(id: Long, personVO: PersonVO): PersonVO {
        logger.info("Updating person with ID $id")
        val existingPerson = repository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Person with id $id not found")
        }

        existingPerson.apply {
            firstName = personVO.firstName
            lastName = personVO.lastName
            address = personVO.address
            gender = personVO.gender
            birthDay = personVO.birthDay
        }

        val updated = repository.save(existingPerson)
        val personVOResult: PersonVO = ModelMapperWrapper.map(updated, PersonVO::class.java)
        addHateoasLinks(personVOResult)
        return personVOResult
    }

    fun deleteById(id: Long) {
        logger.info("Deleting person with ID $id")
        if (!repository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Person with id $id not found")
        }
        repository.deleteById(id)
    }

    fun findAll(): List<PersonVO> {
        logger.info("Finding all persons")
        val persons = repository.findAll()
        val personVOList = ModelMapperWrapper.mapList(persons, PersonVO::class.java)

        personVOList.forEach { addHateoasLinks(it) }

        return personVOList
    }

    private fun addHateoasLinks(personVO: PersonVO) {
        val selfLink = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(selfLink)

        // Você pode adicionar outros links úteis, como:
        // val allPersonsLink = linkTo(methodOn(PersonController::class.java).findAll()).withRel("all-persons")
        // personVO.add(allPersonsLink)
    }
}

