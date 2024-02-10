package leoleite.creditapplication.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import leoleite.creditapplication.system.controller.dto.CustomerDto
import leoleite.creditapplication.system.controller.dto.CustomerUpdateDto
import leoleite.creditapplication.system.entity.Customer
import leoleite.creditapplication.system.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CustomerControllerTest {
  @Autowired private lateinit var customerRepository: CustomerRepository
  @Autowired private lateinit var mockMvc: MockMvc
  @Autowired private lateinit var objectMapper: ObjectMapper

  companion object {
    const val URL: String = "/api/customers"
  }

  @BeforeEach fun setup() = customerRepository.deleteAll()
  @AfterEach fun tearDown() = customerRepository.deleteAll()

  @Test
  fun `should create a customer and return 201 status`() {
    //given
    val customerDto: CustomerDto = builderCustomeDto()
    val customerDtoAsString: String = objectMapper.writeValueAsString(customerDto)
    //when //then
    mockMvc.perform(MockMvcRequestBuilders.post(URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(customerDtoAsString))
      .andExpect(MockMvcResultMatchers.status().isCreated )
      .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("German"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Cano"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("48363722081"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("cano@email.com"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("3564218"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua do Cano"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should not allow customer with same email or cpf and return 409 status`() {
    //given
    customerRepository.save(builderCustomeDto().toEntity())
    val customerUpdateDto: CustomerUpdateDto = buiderCustomerUpdateDto()
    val customerDto: CustomerDto = builderCustomeDto()
    val customerDtoAsString: String = objectMapper.writeValueAsString(customerDto)
    //when //then
    mockMvc.perform(MockMvcRequestBuilders.post(URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(customerDtoAsString))
      .andExpect(MockMvcResultMatchers.status().isConflict)
      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(409))
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should not allow to register a customer with empty fields and return 400 status`() {
    //given
    val customerDto: CustomerDto = builderCustomeDto(firstName = "")
    val customerDtoAsString: String = objectMapper.writeValueAsString(customerDto)
    //when //then
    mockMvc.perform(MockMvcRequestBuilders.post(URL)
      .contentType(MediaType.APPLICATION_JSON)
      .content(customerDtoAsString))
      .andExpect(MockMvcResultMatchers.status().isBadRequest)
      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should find a customer by customerId`() {
    //given
    val customer: Customer = customerRepository.save(builderCustomeDto().toEntity())
    //when //then
    mockMvc.perform(MockMvcRequestBuilders.get("$URL/${customer.id}")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk)
      .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("German"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Cano"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("48363722081"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("cano@email.com"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("3564218"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua do Cano"))
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should not find a customer with a invalid customerId and return a 400 status`() {
    //given
    val invalidId: Long = 5L
    //when //then
    mockMvc.perform(MockMvcRequestBuilders.get("$URL/${invalidId}")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isBadRequest)
      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should delete a customer by customerId`() {
    //given
    val customer: Customer = customerRepository.save(builderCustomeDto().toEntity())
    //when //then
    mockMvc.perform(MockMvcRequestBuilders.delete("$URL/${customer.id}")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isNoContent)
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should return 400 status when try to delete a invalid customerId`() {
    //given
    val invalidId: Long = 5L
    //when //then
    mockMvc.perform(MockMvcRequestBuilders.delete("$URL/$invalidId")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isBadRequest)
      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should update a customer and return 200 status`() {
    //given
    val customer: Customer = customerRepository.save(builderCustomeDto().toEntity())
    val customerUpdateDto: CustomerUpdateDto = buiderCustomerUpdateDto()
    val customerUpdateDtoAsString: String = objectMapper.writeValueAsString(customerUpdateDto)
    //when //then
    mockMvc.perform(MockMvcRequestBuilders.patch("$URL?customerId=${customer.id}")
      .contentType(MediaType.APPLICATION_JSON)
      .content(customerUpdateDtoAsString))
      .andExpect(MockMvcResultMatchers.status().isOk)
      .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("GermanUpdated"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("CanoUpdated"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.income").value(900000.0))
      .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("cano@email.com"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("3564218"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Nova Rua do Cano"))
      .andDo(MockMvcResultHandlers.print())
  }

  @Test
  fun `should not update a customer with invalid customerId and return 400 status`() {
    //given
    val invalidId: Long = 10L
    val customerUpdateDto: CustomerUpdateDto = buiderCustomerUpdateDto()
    val customerUpdateDtoAsString: String = objectMapper.writeValueAsString(customerUpdateDto)
    //when //then
    mockMvc.perform(
      MockMvcRequestBuilders.patch("$URL?customerId=$invalidId")
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerUpdateDtoAsString))
        .andExpect(MockMvcResultMatchers.status().isBadRequest)
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andDo(MockMvcResultHandlers.print())
  }

  private fun builderCustomeDto(
    firstName: String = "German",
    lastName: String = "Cano",
    cpf: String = "48363722081",
    income: BigDecimal = BigDecimal.valueOf(700000.0),
    email: String = "cano@email.com",
    password: String = "12345",
    zipCode: String = "3564218",
    street: String = "Rua do Cano",
  ): CustomerDto = CustomerDto (
    firstName = firstName,
    lastName = lastName,
    cpf = cpf,
    income = income,
    email = email,
    password = password,
    zipCode = zipCode,
    street = street
  )

  private fun buiderCustomerUpdateDto(
    firstName: String = "GermanUpdated",
    lastName: String = "CanoUpdated",
    income: BigDecimal = BigDecimal.valueOf(900000.0),
    zipCode: String = "3564218",
    street: String = "Nova Rua do Cano",
  ): CustomerUpdateDto = CustomerUpdateDto (
    firstName = firstName,
    lastName = lastName,
    income = income,
    zipCode = zipCode,
    street = street
  )
}