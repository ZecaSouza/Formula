package com.example.Formula.service

import com.example.Formula.controller.BookController
import com.example.Formula.data.vo.v1.BookVO
import com.example.Formula.exceptions.RequiredObjectIsNullException
import com.example.Formula.mapper.ModelMapperWrapper
import com.example.Formula.model.Book
import com.example.Formula.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.logging.Logger

@Service
class BookService(
    @Autowired private val repository: BookRepository,
) {

    private val logger: Logger = Logger.getLogger(BookService::class.java.name)

    fun findById(id: Long): BookVO {
        logger.info("Finding one book with ID $id")

        val book = repository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found")
        }

        val vo = ModelMapperWrapper.map(book, BookVO::class.java)

        addHateoasLinks(vo)

        return vo
    }

    fun findAll(): List<BookVO> {
        logger.info("Finding all books")
        val books = repository.findAll()
        val bookVOList = ModelMapperWrapper.mapList(books, BookVO::class.java)

        bookVOList.forEach { addHateoasLinks(it) }

        return bookVOList
    }

    fun create(bookVO: BookVO?): BookVO {
        if (bookVO == null) throw RequiredObjectIsNullException()
        logger.info("Creating book: ${bookVO.title} by ${bookVO.author}")
        val entity: Book = ModelMapperWrapper.map(bookVO, Book::class.java)
        val saved = repository.save(entity)
        val voResult: BookVO = ModelMapperWrapper.map(saved, BookVO::class.java)
        addHateoasLinks(voResult)
        return voResult
    }

    fun updateById(id: Long, bookVO: BookVO?): BookVO {
        if (bookVO == null) throw RequiredObjectIsNullException()
        logger.info("Updating book with ID $id")
        val existingBook = repository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id $id not found")
        }

        existingBook.apply {
            title = bookVO.title
            author = bookVO.author
            price = bookVO.price
            launchDate = bookVO.launchDate
        }

        val updated = repository.save(existingBook)
        val voResult: BookVO = ModelMapperWrapper.map(updated, BookVO::class.java)
        addHateoasLinks(voResult)
        return voResult
    }

    fun deleteById(id: Long) {
        logger.info("Deleting book with ID $id")
        if (!repository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id $id not found")
        }
        repository.deleteById(id)
    }

    private fun addHateoasLinks(bookVO: BookVO) {
        val selfLink = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(selfLink)
    }
}
