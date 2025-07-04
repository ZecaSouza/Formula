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
        logger.info("find one person with ID $id")
        val person = repository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Person with id $id not found")
        }
        val personVO: PersonVO =  ModelMapperWrapper.map(person, PersonVO::class.java)

        val withSelfRel = linkTo(PersonController::class.java ).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun create(personVO: PersonVO): PersonVO {
        logger.info("Creating person: ${personVO.firstName}")
        val entity: Person = ModelMapperWrapper.map(personVO, Person::class.java)
        val saved = repository.save(entity)
        return ModelMapperWrapper.map(saved, PersonVO::class.java)
    }

    fun updateById(id: Long, personVO: PersonVO): PersonVO {
        val existingPerson = repository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Person with id $id not found")
        }

        logger.info("Updating person with id $id")
        existingPerson.apply {
            firstName = personVO.firstName
            lastName = personVO.lastName
            address = personVO.address
            gender = personVO.gender
            birthDay = personVO.birthDay
        }

        val updated = repository.save(existingPerson)
        return ModelMapperWrapper.map(updated, PersonVO::class.java)
    }

    fun deleteById(id: Long) {
        if (!repository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Person with id $id not found")
        }
        logger.info("Deleting person with id $id")
        repository.deleteById(id)
    }

    fun findAll(): List<PersonVO> {
        val persons = repository.findAll()
        return ModelMapperWrapper.mapList(persons, PersonVO::class.java)
    }
}
