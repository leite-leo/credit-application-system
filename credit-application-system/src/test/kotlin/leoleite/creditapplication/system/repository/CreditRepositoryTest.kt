package leoleite.creditapplication.system.repository

import leoleite.creditapplication.system.entity.Address
import leoleite.creditapplication.system.entity.Credit
import leoleite.creditapplication.system.entity.Customer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {
  @Autowired lateinit var creditRepository: CreditRepository
  @Autowired lateinit var testEntityManager: TestEntityManager

  private lateinit var customer: Customer
  private lateinit var credit1: Credit
  private lateinit var credit2:Credit

  @BeforeEach fun setup () {
    customer = testEntityManager.merge(buildCustomer())
    credit1 = testEntityManager.persist(buildCredit(customer = customer))
    credit2 = testEntityManager.persist(buildCredit(customer = customer))
  }

  @Test
  fun `should find a credit by creditCode` () {
    //given
    val creditCode1 = UUID.fromString("794e7257-4573-4acd-9e6a-b0ce73375524")
    val creditCode2 = UUID.fromString("49f740be-46a7-449b-84e7-ff5b7986d7ef")
    credit1.creditCode = creditCode1
    credit2.creditCode = creditCode2
    //when
    val fakeCredit1: Credit = creditRepository.findByCreditCode(creditCode1)!!
    val fakeCredit2: Credit = creditRepository.findByCreditCode(creditCode2)!!
    //then
    Assertions.assertThat(fakeCredit1).isNotNull
    Assertions.assertThat(fakeCredit2).isNotNull
    Assertions.assertThat(fakeCredit1).isSameAs(credit1)
    Assertions.assertThat(fakeCredit2).isSameAs(credit2)
  }

  @Test
  fun `should find all credits by customerId` () {
    //given
    val customerId: Long = 1L
    //when
    val creditList: List<Credit> = creditRepository.findAllByCustomerId(customerId)
    //then
    Assertions.assertThat(creditList).isNotNull
    Assertions.assertThat(creditList.size).isEqualTo(2)
    Assertions.assertThat(creditList).contains(credit1, credit2)
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
    customer: Customer
  ) = Credit (
    creditValue = creditValue,
    dayFirstInstallment = dayFirstInstallment,
    numberOfInstallment = numberOfInstallment,
    customer = customer
  )
}

