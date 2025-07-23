package com.example.Formula.controller

import com.example.Formula.data.vo.v1.PersonVO
import com.example.Formula.service.PersonService
import com.example.Formula.util.MediaType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/person")
@Tag(name = "Peaple", description = "Endpoints para gerenciamento de pessoas. Permite criar, atualizar, buscar, listar e excluir registros de pessoas.")
class PersonController(
    @Autowired private val personService: PersonService
) {

    @Operation(summary = "Buscar uma pessoa pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
            ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping(
        "/{id}",
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    fun findPersonById(
        @Parameter(description = "ID da pessoa a ser buscada", required = true)
        @PathVariable id: Long
    ): PersonVO {
        return personService.findById(id)
    }

    @Operation(summary = "Criar uma nova pessoa")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
            ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    fun createPerson(
        @Parameter(description = "Objeto com os dados da nova pessoa", required = true)
        @RequestBody person: PersonVO
    ): PersonVO {
        return personService.create(person)
    }

    @Operation(summary = "Atualizar uma pessoa existente pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
            ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
            ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PutMapping(
        "/{id}",
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    fun updatePersonById(
        @Parameter(description = "ID da pessoa a ser atualizada", required = true)
        @PathVariable id: Long,
        @Parameter(description = "Objeto com os novos dados da pessoa", required = true)
        @RequestBody person: PersonVO
    ): PersonVO {
        return personService.updateById(id, person)
    }

    @Operation(summary = "Excluir uma pessoa pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Pessoa excluída com sucesso"),
            ApiResponse(responseCode = "404", description = "Pessoa não encontrada"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @DeleteMapping(
        "/{id}",
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    fun deletePersonById(
        @Parameter(description = "ID da pessoa a ser excluída", required = true)
        @PathVariable id: Long
    ): ResponseEntity<*> {
        personService.deleteById(id)
        return ResponseEntity.noContent().build<Any>()
    }

    @Operation(summary = "Listar todas as pessoas cadastradas")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Lista de pessoas retornada com sucesso"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping(
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    fun findAllPerson(): List<PersonVO> {
        return personService.findAll()
    }
}
