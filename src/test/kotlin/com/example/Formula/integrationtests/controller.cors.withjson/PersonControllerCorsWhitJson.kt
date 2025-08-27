package com.example.Formula.integrationtests.controller.cors.withjson

import com.example.Formula.integrationtests.ConfigsTest
import com.example.Formula.integrationtests.testContainer.AbstractIntegrationTest
import com.example.Formula.integrationtests.vo.PersonVO
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class PersonControllerCorsWithJson : AbstractIntegrationTest() {

	private lateinit var specification: RequestSpecification
	private lateinit var objectMapper: ObjectMapper
	private lateinit var personVO: PersonVO

	@BeforeEach
	fun setupTest() {
		objectMapper = ObjectMapper().apply {
			disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			registerModule(JavaTimeModule())
			disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
		}
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

		personVO = PersonVO(
			key = 0,
			firstName = "José",
			lastName = "Souza",
			address = "123 Main Street",
			gender = "Male",
			birthDay = LocalDate.of(1990, 1, 1)
		)
	}

	@Test
	@Order(1)
	fun testCreate() {
		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(ConfigsTest.HEADER_ORIGIN, "http://localhost:8080")
			.setBasePath("/api/person/")
			.setPort(ConfigsTest.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL))
			.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()

		val content = given()
			.spec(specification)
			.contentType(ConfigsTest.CONTENT_TYPE_JSON)
			.body(objectMapper.writeValueAsString(personVO))
			.`when`()
			.post()
			.then()
			.statusCode(201)
			.extract()
			.body()
			.asString()

		val createdPerson = objectMapper.readValue(content, PersonVO::class.java)

		Assertions.assertNotNull(createdPerson.key, "O ID não deve ser nulo")
		Assertions.assertNotNull(createdPerson.firstName, "O firstName não deve ser nulo")
		Assertions.assertNotNull(createdPerson.lastName, "O lastName não deve ser nulo")
		Assertions.assertEquals(personVO.firstName, createdPerson.firstName, "O firstName deve ser igual ao cadastrado")
		Assertions.assertEquals(personVO.lastName, createdPerson.lastName, "O lastName deve ser igual ao cadastrado")
		Assertions.assertEquals(personVO.address, createdPerson.address, "O endereço deve ser igual ao cadastrado")
		Assertions.assertEquals(personVO.gender, createdPerson.gender, "O gênero deve ser igual ao cadastrado")
	}

	private fun mockPerson() {
		personVO.firstName = "José"
		personVO.lastName = "Souza"
		personVO.address = "123 Main Street"
		personVO.gender = "Male"
		personVO.birthDay = LocalDate.of(1990, 1, 1)
	}
}
