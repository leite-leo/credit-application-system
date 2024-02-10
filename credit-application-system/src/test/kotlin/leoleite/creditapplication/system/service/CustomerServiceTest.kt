package leoleite.creditapplication.system.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import leoleite.creditapplication.system.entity.Address
import leoleite.creditapplication.system.entity.Customer
import leoleite.creditapplication.system.exceptions.BussinessException
import leoleite.creditapplication.system.repository.CustomerRepository
import leoleite.creditapplication.system.service.implementation.CustomerService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.util.*

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerServiceTest {
  @MockK lateinit var customerRepository: CustomerRepository
  @InjectMockKs lateinit var customerService: CustomerService

  @Test
  fun `should create a customer` (){
    //given
    val fakeCustomer: Customer = buildCustomer()
    every { customerRepository.save(any()) } returns fakeCustomer
    //when
    val actual: Customer = customerService.save(fakeCustomer)
    //then
    Assertions.assertThat(actual).isNotNull
    Assertions.assertThat(actual).isSameAs(fakeCustomer)
    verify(exactly = 1) { customerRepository.save(fakeCustomer)  }
  }

  @Test
  fun `should find customer by id`() {
    //given
    val fakeId: Long = Random().nextLong()
    val fakeCustomer: Customer = buildCustomer(id = fakeId)
    every { customerRepository.findById(fakeId) } returns Optional.of(fakeCustomer)
    //when
    val actual: Customer = customerService.findById(fakeId)
    //then
    Assertions.assertThat(actual).isNotNull
    Assertions.assertThat(actual).isExactlyInstanceOf(Customer::class.java)
    Assertions.assertThat(actual).isSameAs(fakeCustomer)
    verify(exactly = 1) { customerRepository.findById(fakeId)  }
  }

  @Test
  fun `should not find customer by id and throw a BussinessException`() {
    //given
    val fakeId: Long = Random().nextLong()
    every { customerRepository.findById(fakeId) } returns Optional.empty()
    //when an then
    Assertions.assertThatExceptionOfType(BussinessException::class.java)
      .isThrownBy { customerService.findById(fakeId) }
      .withMessage("Id $fakeId not found!")
    verify(exactly = 1 ) { customerRepository.findById(fakeId)  }
  }

  @Test
  fun `should delete customer by id`() {
    //given
    val fakeId: Long = Random().nextLong()
    val fakeCustomer: Customer = buildCustomer(id = fakeId)
    every { customerRepository.findById(fakeId) } returns Optional.of(fakeCustomer)
    every { customerRepository.delete(fakeCustomer) } just runs
    //when
    customerService.delete(fakeId)
    //then
    verify(exactly = 1) { customerRepository.findById(fakeId) }
    verify(exactly = 1) { customerRepository.delete(fakeCustomer) }
  }

  private fun buildCustomer(
    firstName: String = "German",
    lastName: String = "Cano",
    cpf: String = "25896374112",
    income: BigDecimal = BigDecimal.valueOf(700000.0),
    email: String = "cano@email.com",
    password: String = "12345",
    zipCode: String = "3564218" ,
    street: String = "Rua do Cano",
    id: Long = 1L
  ) = Customer (
    firstName = firstName,
    lastName = lastName,
    cpf = cpf,
    income = income,
    email = email,
    password = password,
    address = Address(
      zipCode = zipCode,
      street = street,
    ),
    id = id
  )

}