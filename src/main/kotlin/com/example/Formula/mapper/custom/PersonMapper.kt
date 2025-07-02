package com.example.Formula.mapper.custom;

import com.example.Formula.data.vo.v1.PersonVO;
import com.example.Formula.model.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonMapper {

    fun mapEntityToVO(person: Person): PersonVO {
        return PersonVO(
            key = person.id,
            firstName = person.firstName,
            lastName = person.lastName,
            address = person.address,
            gender = person.gender,
            birthDay = person.birthDay
        )
    }

    fun mapVOToEntity(vo: PersonVO): Person {
        val person = Person()
        person.id = vo.key
        person.firstName = vo.firstName
        person.lastName = vo.lastName
        person.address = vo.address
        person.gender = vo.gender
        person.birthDay = vo.birthDay
        return person
    }

}
