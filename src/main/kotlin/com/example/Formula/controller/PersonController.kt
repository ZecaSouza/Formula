package com.example.Formula.controller

import com.example.Formula.data.vo.v1.PersonVO
import com.example.Formula.service.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(
    @Autowired private val personService: PersonService
) {

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE,
                                  MediaType.APPLICATION_XML_VALUE])
    fun findPersonById(@PathVariable id: Long): PersonVO {
        return personService.findById(id)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
                 produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun createPerson(@RequestBody person: PersonVO): PersonVO {
        return personService.create(person)
    }

    @PutMapping("/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
                                  produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun updatePersonById(
        @PathVariable id: Long,
        @RequestBody person: PersonVO
    ): PersonVO {
        return personService.updateById(id, person)
    }

    @DeleteMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE,
                                                 MediaType.APPLICATION_XML_VALUE])
    fun deletePersonById(@PathVariable id: Long): ResponseEntity<*> {
        personService.deleteById(id)
        return ResponseEntity.noContent().build<Any>()
    }


    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE])
    fun findAllPerson(): List<PersonVO> {
        return personService.findAll()
    }
}