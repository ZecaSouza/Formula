package com.example.Formula.mapper.custom;

import com.example.Formula.data.vo.v1.PersonVO;
import com.example.Formula.model.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonMapper {

    fun mapEntityToVO(person: Person): PersonVO {
        return PersonVO(
            id = person.id,
            firstName = person.firstName,
            lastName = person.lastName,
            address = person.address,
            gender = person.gender,
            birthDay = person.birthDay
        )
    }

    fun mapVOToEntity(vo: PersonVO): Person {
        return Person(
            id = vo.id,
            firstName = vo.firstName,
            lastName = vo.lastName,
            address = vo.address,
            gender = vo.gender,
            birthDay = vo.birthDay
        )
    }

}
