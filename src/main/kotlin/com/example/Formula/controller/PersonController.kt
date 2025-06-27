package com.example.Formula.controller

import com.example.Formula.data.vo.v1.PersonVO
import com.example.Formula.service.PersonService
import com.example.Formula.util.MediaType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(
    @Autowired private val personService: PersonService
) {

    @GetMapping(
        "/{id}",
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    fun findPersonById(@PathVariable id: Long): PersonVO {
        return personService.findById(id)
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    fun createPerson(@RequestBody person: PersonVO): PersonVO {
        return personService.create(person)
    }

    @PutMapping(
        "/{id}",
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    fun updatePersonById(@PathVariable id: Long, @RequestBody person: PersonVO): PersonVO {
        return personService.updateById(id, person)
    }

    @DeleteMapping(
        "/{id}",
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    fun deletePersonById(@PathVariable id: Long): ResponseEntity<*> {
        personService.deleteById(id)
        return ResponseEntity.noContent().build<Any>()
    }

    @GetMapping(
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    fun findAllPerson(): List<PersonVO> {
        return personService.findAll()
    }
}
