package leoleite.creditapplication.system.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import leoleite.creditapplication.system.entity.Address
import leoleite.creditapplication.system.entity.Credit
import leoleite.creditapplication.system.entity.Customer
import leoleite.creditapplication.system.repository.CreditRepository
import leoleite.creditapplication.system.service.implementation.CreditService
import leoleite.creditapplication.system.service.implementation.CustomerService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CreditServiceTest {
  @MockK lateinit var creditRepository: CreditRepository
  @MockK lateinit var customerService: CustomerService
  @InjectMockKs lateinit var creditService: CreditService

  @Test
  fun `should save a credit` () {
    //given
    val fakeCredit: Credit = buildCredit()
    every { creditRepository.save(any()) } returns fakeCredit
    every { customerService.findById(1L) } returns buildCustomer()
    //when
    val actual: Credit = creditService.save(fakeCredit)
    //then
    Assertions.assertThat(actual).isNotNull
    Assertions.assertThat(actual).isSameAs(fakeCredit)
    verify(exactly = 1) { creditRepository.save(fakeCredit) }
  }

  @Test
  fun `should find all credits from a specific customer by customerId` () {
    //given
    val fakeId: Long = Random().nextLong()
    val fakeCredits: List<Credit> = listOf(buildCredit())
    every { creditRepository.findAllByCustomerId(any()) } returns fakeCredits
    //when
    val actual: List<Credit> = creditService.findAllByCustomer(fakeId)
    //then
    Assertions.assertThat(actual).isNotNull
    Assertions.assertThat(actual).isSameAs(fakeCredits)
    verify(exactly = 1) { creditRepository.findAllByCustomerId(fakeId) }
  }

  @Test
  fun `should find credit by creditCode for a specific customer` () {
    //given
    val fakeCustomerId: Long = Random().nextLong()
    val fakeCreditCode: UUID = UUID.randomUUID()
    val fakeCredit: Credit = buildCredit(customer = buildCustomer(id = fakeCustomerId))
    every { creditRepository.findByCreditCode(fakeCreditCode) } returns fakeCredit
    //when
    val actual: Credit = creditService.findByCreditCode(fakeCustomerId, fakeCreditCode)
    //then
    Assertions.assertThat(actual).isNotNull
    Assertions.assertThat(actual).isSameAs(fakeCredit)
    Assertions.assertThat(actual.customer?.id).isEqualTo(fakeCustomerId)
    verify(exactly = 1) { creditRepository.findByCreditCode(fakeCreditCode) }
  }

  @Test
  fun `should throw exception when customerId does not match` () {
    //given
    val fakeCustomerId: Long = Random().nextLong()
    val fakeCreditCode: UUID = UUID.randomUUID()
    val fakeCredit: Credit = buildCredit(customer = buildCustomer(id = fakeCustomerId + 1)) // ensure different customerId
    every { creditRepository.findByCreditCode(fakeCreditCode) } returns fakeCredit
    //when
    val exception = assertThrows<IllegalArgumentException> {
      creditService.findByCreditCode(fakeCustomerId, fakeCreditCode)
    }
    //then
    Assertions.assertThat(exception.message).isEqualTo("Contact Admin")
    verify(exactly = 1) { creditRepository.findByCreditCode(fakeCreditCode) }
  }

  private fun buildCustomer(
    firstName: String = "German",
    lastName: String = "Cano",
    cpf: String = "25896374112",
    income: BigDecimal = BigDecimal.valueOf(700000.0),
    email: String = "cano@email.com",
    password: String = "12345",
    zipCode: String = "3564218",
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

  private fun buildCredit(
    creditValue: BigDecimal = BigDecimal.valueOf(10000.0),
    dayFirstInstallment: LocalDate = LocalDate.of(2026, 10, 10 ),
    numberOfInstallment: Int = 24,
    customer: Customer = buildCustomer()
  ) = Credit (
    creditValue = creditValue,
    dayFirstInstallment = dayFirstInstallment,
    numberOfInstallment = numberOfInstallment,
    customer = customer
  )
}