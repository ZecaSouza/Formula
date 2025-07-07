package com.example.Formula.mockito.service

import com.example.Formula.data.vo.v1.PersonVO
import com.example.Formula.repository.PersonRepository
import com.example.Formula.service.PersonService
import com.example.Formula.unittest.mapper.MockPerson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
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
    fun create() {
    }

    @Test
    fun updateById() {
    }

    @Test
    fun deleteById() {
    }

    @Test
    fun findAll() {
    }

}