package com.example.Formula

import com.example.Formula.data.vo.v1.PersonVO
import com.example.Formula.mapper.ModelMapperWrapper
import com.example.Formula.model.Person
import com.example.Formula.unittest.mapper.MockPerson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DozerMapperTest {

    private lateinit var inputObject: MockPerson

    @BeforeEach
    fun setUp() {
        inputObject = MockPerson()
    }

    @Test
    fun `test converting Person to PersonVO`() {
        val person: Person = inputObject.mockEntity(1)
        val vo: PersonVO = ModelMapperWrapper.map(person, PersonVO::class.java)

        assertEquals(1L, vo.key)
        assertEquals("First Name Test1", vo.firstName)
        assertEquals("Last name 1", vo.lastName)
        assertEquals("Addres Test1", vo.address)
        assertEquals("FEMALE", vo.gender)
    }

    @Test
    fun `test converting PersonVO to Person`() {
        val vo: PersonVO = inputObject.mockVO(1)
        val person: Person = ModelMapperWrapper.map(vo, Person::class.java)

        assertEquals(1L, person.id)
        assertEquals("First Name Test1", person.firstName)
        assertEquals("Last name 1", person.lastName)
        assertEquals("Addres Test1", person.address)
        assertEquals("FEMALE", person.gender)
    }

    @Test
    fun `test converting Person list to PersonVO list`() {
        val personList = inputObject.mockEntityList()
        val voList: List<PersonVO> = ModelMapperWrapper.mapList(personList, PersonVO::class.java)

        assertEquals(14, voList.size)
        for (i in voList.indices) {
            val vo = voList[i]
            assertEquals(i.toLong(), vo.key)
            assertEquals("First Name Test$i", vo.firstName)
            assertEquals("Last name $i", vo.lastName)
            assertEquals("Addres Test$i", vo.address)
            assertEquals(if (i % 2 == 0) "MALE" else "FEMALE", vo.gender)
        }
    }


    @Test
    fun `test converting PersonVO list to Person list`() {
        val voList = inputObject.mockVOList()
        val personList: List<Person> = ModelMapperWrapper.mapList(voList, Person::class.java)

        assertEquals(14, personList.size)
        for (i in personList.indices) {
            val person = personList[i]
            assertEquals(i.toLong(), person.id)
            assertEquals("First Name Test$i", person.firstName)
            assertEquals("Last name $i", person.lastName)
            assertEquals("Addres Test$i", person.address)
            assertEquals(if (i % 2 == 0) "MALE" else "FEMALE", person.gender)
        }
    }
}
