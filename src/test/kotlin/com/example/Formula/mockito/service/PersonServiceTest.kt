package com.example.Formula.mockito.service

import com.example.Formula.data.vo.v1.PersonVO
import com.example.Formula.exceptions.RequiredObjectIsNullException
import com.example.Formula.repository.PersonRepository
import com.example.Formula.service.PersonService
import com.example.Formula.unittest.mapper.MockPerson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.Mockito.verify
import java.lang.Exception
import java.util.Optional
import kotlin.test.assertEquals
import kotlin.test.assertTrue



@ExtendWith(MockitoExtension::class)
class PersonServiceTest {
    private lateinit var inputObject: MockPerson

    @InjectMocks
    private lateinit var service: PersonService

    @Mock
    private lateinit var repository: PersonRepository

    @BeforeEach
    fun setUpMock() {
        inputObject = MockPerson()
    }

    @Test
    fun findById() {
        val personId = 1L
        val person = inputObject.mockEntity(1).apply {
            id = personId
        }

        `when`(repository.findById(personId)).thenReturn(Optional.of(person))

        val result = service.findById(personId)

        assertNotNull(result)
        assertEquals(personId, result.key)
        assertTrue(result.links.any { it.rel.value() == "self" })
        assertEquals("Addres Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last name 1", result.lastName)
        assertEquals("FEMALE", result.gender)
    }

    @Test
    fun createWithNullPerson() {
        val exception = assertThrows<RequiredObjectIsNullException> {
            service.create(null)
        }

        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message
        assertTrue { actualMessage!!.contains(expectedMessage) }
    }

    @Test
    fun create() {
        val personId = 1L
        val vo = inputObject.mockVO(1).apply { key = personId }

        val entity = inputObject.mockEntity(1).apply { id = personId }

        `when`(repository.save(any())).thenReturn(entity)

        val result = service.create(vo)

        assertNotNull(result)
        assertEquals(personId, result.key)
        assertTrue(result.links.any { it.rel.value() == "self" })
        assertEquals("Addres Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last name 1", result.lastName)
        assertEquals("FEMALE", result.gender)
    }

    @Test
    fun updateWithNullPerson() {
        val exception = assertThrows<RequiredObjectIsNullException> {
            service.create(null)
        }

        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message
        assertTrue { actualMessage!!.contains(expectedMessage) }
    }

    @Test
    fun updateById() {
        val personId = 1L
        val vo = inputObject.mockVO(1).apply { key = personId }

        val entity = inputObject.mockEntity(1).apply { id = personId }

        `when`(repository.findById(personId)).thenReturn(Optional.of(entity))
        `when`(repository.save(any())).thenReturn(entity)

        val result = service.updateById(personId, vo)

        assertNotNull(result)
        assertEquals(personId, result.key)
        assertTrue(result.links.any { it.rel.value() == "self" })
        assertEquals("Addres Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last name 1", result.lastName)
        assertEquals("FEMALE", result.gender)

    }

    @Test
    fun deleteById() {
        val id = 1L
        `when`(repository.existsById(id)).thenReturn(true)

        service.deleteById(id)

        verify(repository).deleteById(id)
    }

    @Test
    fun findAll() {
        val person = inputObject.mockEntity(1)
        val person2 = inputObject.mockEntity(2).apply {
            id = 2
            address = "Different Address"
            firstName = "Different First Name"
            lastName = "Different Last Name"
            gender = "MALE"
        }

        val personList = listOf(person, person2)

        `when`(repository.findAll()).thenReturn(personList)

        val result = service.findAll()

        assertNotNull(result)
        assertEquals(2, result.size, "Deve retornar 2 itens")

        // Verifica o primeiro item
        val firstPerson = result[0]
        assertEquals(1L, firstPerson.key)
        assertTrue(firstPerson.links.any { it.rel.value() == "self" }, "Deve ter link 'self'")
        assertEquals("Addres Test1", firstPerson.address)
        assertEquals("First Name Test1", firstPerson.firstName)
        assertEquals("Last name 1", firstPerson.lastName)
        assertEquals("FEMALE", firstPerson.gender)

        // Verifica o segundo item
        val secondPerson = result[1]
        assertEquals(2L, secondPerson.key)
        assertTrue(secondPerson.links.any { it.rel.value() == "self" }, "Deve ter link 'self'")
        assertEquals("Different Address", secondPerson.address)
        assertEquals("Different First Name", secondPerson.firstName)
        assertEquals("Different Last Name", secondPerson.lastName)
        assertEquals("MALE", secondPerson.gender)
    }

}