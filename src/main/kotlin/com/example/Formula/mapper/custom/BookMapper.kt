package com.example.Formula.mapper.custom

import com.example.Formula.data.vo.v1.BookVO
import com.example.Formula.model.Book
import org.springframework.stereotype.Service

@Service
class BookMapper {

    fun mapEntityToVO(book: Book): BookVO {
        return BookVO(
            key = book.id,
            author = book.author,
            title = book.title,
            launchDate = book.launchDate,
            price = book.price
        )
    }

    fun mapVOToEntity(vo: BookVO): Book {
        val book = Book()
        book.id = vo.key
        book.author = vo.author
        book.title = vo.title
        book.launchDate = vo.launchDate
        book.price = vo.price
        return book
    }
}
