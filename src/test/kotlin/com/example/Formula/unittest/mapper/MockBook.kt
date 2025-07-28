package com.example.Formula.unittest.mapper

import com.example.Formula.data.vo.v1.BookVO
import com.example.Formula.model.Book
import java.math.BigDecimal
import java.time.LocalDateTime

class MockBook {

    fun mockEntity(): Book = mockEntity(0)

    fun mockVO(): BookVO = mockVO(0)

    fun mockEntityList(): List<Book> =
        (0..13).map { mockEntity(it) }

    fun mockVOList(): List<BookVO> =
        (0..13).map { mockVO(it) }

    fun mockEntity(number: Int): Book {
        val book = Book()
        book.id = number.toLong()
        book.author = "Author Test $number"
        book.title = "Title Test $number"
        book.launchDate = LocalDateTime.of(2020, (number % 12) + 1, (number % 28) + 1, 10, 0)
        book.price = BigDecimal.valueOf(50.0 + number)
        return book
    }

    fun mockVO(number: Int): BookVO {
        return BookVO(
            key = number.toLong(),
            author = "Author Test $number",
            title = "Title Test $number",
            launchDate = LocalDateTime.of(2020, (number % 12) + 1, (number % 28) + 1, 10, 0),
            price = BigDecimal.valueOf(50.0 + number)
        )
    }
}
