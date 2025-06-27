package com.example.Formula.unittest.mapper

import com.example.Formula.data.vo.v1.PersonVO
import com.example.Formula.model.Person

class MockPerson {

    fun mockEntity(): Person{
        return mockEntity(0)
    }

    fun mockVO(): PersonVO{
        return mockVO(0)
    }

    fun mockEntityList(): ArrayList<Person>{
        val persons: ArrayList<Person> = ArrayList<Person>()
        for (i in 0..13){
            persons.add(mockEntity(i))
        }
        return persons
    }

    fun mockVOList(): ArrayList<PersonVO>{
        val persons: ArrayList<PersonVO> = ArrayList()
        for (i in 0..13){
            persons.add(mockVO(i))
        }
        return persons
    }

    fun mockEntity(number: Int): Person{
        val person = Person()
        person.address = "Addres Test$number"
        person.firstName = "First Name Test$number"
        person.gender = if (number % 2 == 0) "MALE" else "FEMALE"
        person.id = number.toLong()
        person.lastName = "Last name $number"
        return person
    }

    fun mockVO(number: Int): PersonVO {
        val person = PersonVO()
        person.address = "Addres Test$number"
        person.firstName = "First Name Test$number"
        person.gender = if (number % 2 == 0) "MALE" else "FEMALE"
        person.id = number.toLong()
        person.lastName = "Last name $number"
        return person
    }

    
}