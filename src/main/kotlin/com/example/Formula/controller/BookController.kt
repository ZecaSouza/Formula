package com.example.Formula.controller

import com.example.Formula.data.vo.v1.BookVO
import com.example.Formula.service.BookService
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
@RequestMapping("/api/book")
@Tag(
    name = "Books",
    description = "Endpoints para gerenciamento de livros. Permite criar, atualizar, buscar, listar e excluir registros de livros."
)
class BookController(
    @Autowired private val bookService: BookService
) {

    @Operation(summary = "Buscar um livro pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Livro encontrado"),
            ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping(
        "/{id}",
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    fun findBookById(
        @Parameter(description = "ID do livro a ser buscado", required = true)
        @PathVariable id: Long
    ): BookVO {
        return bookService.findById(id)
    }

    @Operation(summary = "Criar um novo livro")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Livro criado com sucesso"),
            ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    fun createBook(
        @Parameter(description = "Objeto com os dados do novo livro", required = true)
        @RequestBody book: BookVO
    ): BookVO {
        return bookService.create(book)
    }

    @Operation(summary = "Atualizar um livro existente pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
            ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PutMapping(
        "/{id}",
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    fun updateBookById(
        @Parameter(description = "ID do livro a ser atualizado", required = true)
        @PathVariable id: Long,
        @Parameter(description = "Objeto com os novos dados do livro", required = true)
        @RequestBody book: BookVO
    ): BookVO {
        return bookService.updateById(id, book)
    }

    @Operation(summary = "Excluir um livro pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Livro excluído com sucesso"),
            ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @DeleteMapping(
        "/{id}",
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    fun deleteBookById(
        @Parameter(description = "ID do livro a ser excluído", required = true)
        @PathVariable id: Long
    ): ResponseEntity<*> {
        bookService.deleteById(id)
        return ResponseEntity.noContent().build<Any>()
    }

    @Operation(summary = "Listar todos os livros cadastrados")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping(
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    fun findAllBooks(): List<BookVO> {
        return bookService.findAll()
    }
}
