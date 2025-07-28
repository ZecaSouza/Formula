package com.example.Formula.mockito.service

import com.example.Formula.data.vo.v1.BookVO
import com.example.Formula.exceptions.RequiredObjectIsNullException
import com.example.Formula.model.Book
import com.example.Formula.repository.BookRepository
import com.example.Formula.service.BookService
import com.example.Formula.unittest.mapper.MockBook
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class BookServiceTest {

    private lateinit var inputObject: MockBook

    @InjectMocks
    private lateinit var service: BookService

    @Mock
    private lateinit var repository: BookRepository

    @BeforeEach
    fun setUpMock() {
        inputObject = MockBook()
    }

    @Test
    fun findById() {
        val bookId = 1L
        val book = inputObject.mockEntity(1).apply { id = bookId }

        Mockito.`when`(repository.findById(bookId)).thenReturn(Optional.of(book))

        val result = service.findById(bookId)

        assertNotNull(result)
        assertEquals(bookId, result.key)
        assertEquals("Author Test 1", result.author)
        assertEquals("Title Test 1", result.title)
        assertEquals(book.launchDate, result.launchDate)
        assertEquals(book.price, result.price)
        assertTrue(result.links.any { it.rel.value() == "self" })
    }

    @Test
    fun createWithNullBook() {
        val exception = assertThrows<RequiredObjectIsNullException> {
            service.create(null)
        }

        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun create() {
        val bookId = 1L
        val vo = inputObject.mockVO(1).apply { key = bookId }
        val entity = inputObject.mockEntity(1).apply { id = bookId }

        Mockito.`when`(repository.save(Mockito.any())).thenReturn(entity)

        val result = service.create(vo)

        assertNotNull(result)
        assertEquals(bookId, result.key)
        assertEquals("Author Test 1", result.author)
        assertEquals("Title Test 1", result.title)
        assertEquals(entity.launchDate, result.launchDate)
        assertEquals(entity.price, result.price)
        assertTrue(result.links.any { it.rel.value() == "self" })
    }

    @Test
    fun updateWithNullBook() {
        val exception = assertThrows<RequiredObjectIsNullException> {
            service.updateById(1L, bookVO = null)
        }

        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun updateById() {
        val bookId = 1L
        val vo = inputObject.mockVO(1).apply { key = bookId }
        val entity = inputObject.mockEntity(1).apply { id = bookId }

        Mockito.`when`(repository.findById(bookId)).thenReturn(Optional.of(entity))
        Mockito.`when`(repository.save(Mockito.any())).thenReturn(entity)

        val result = service.updateById(bookId, vo)

        assertNotNull(result)
        assertEquals(bookId, result.key)
        assertEquals("Author Test 1", result.author)
        assertEquals("Title Test 1", result.title)
        assertEquals(entity.launchDate, result.launchDate)
        assertEquals(entity.price, result.price)
        assertTrue(result.links.any { it.rel.value() == "self" })
    }

    @Test
    fun deleteById() {
        val bookId = 1L

        Mockito.`when`(repository.existsById(bookId)).thenReturn(true)

        service.deleteById(bookId)

        verify(repository).deleteById(bookId)
    }

    @Test
    fun findAll() {
        val book1 = inputObject.mockEntity(1)
        val book2 = inputObject.mockEntity(2).apply {
            id = 2L
            author = "Different Author"
            title = "Different Title"
        }

        val bookList = listOf(book1, book2)

        Mockito.`when`(repository.findAll()).thenReturn(bookList)

        val result = service.findAll()

        assertNotNull(result)
        assertEquals(2, result.size)

        val firstBook = result[0]
        assertEquals(1L, firstBook.key)
        assertEquals("Author Test 1", firstBook.author)
        assertEquals("Title Test 1", firstBook.title)
        assertTrue(firstBook.links.any { it.rel.value() == "self" })

        val secondBook = result[1]
        assertEquals(2L, secondBook.key)
        assertEquals("Different Author", secondBook.author)
        assertEquals("Different Title", secondBook.title)
        assertTrue(secondBook.links.any { it.rel.value() == "self" })
    }
}
