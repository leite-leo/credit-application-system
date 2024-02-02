package leoleite.creditapplication.system.controller.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import leoleite.creditapplication.system.entity.Address
import leoleite.creditapplication.system.entity.Customer
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDto(
  @field:NotEmpty(message = "First Name field is empty") val firstName: String,
  @field:NotEmpty(message = "Last Name field is empty") val lastName: String,
  @field:CPF(message = "Invalid CPF")
  @field:NotEmpty(message = "CPF field is empty") val cpf: String,
  @field:NotNull(message = "Income field is empty") val income: BigDecimal,
  @field:NotEmpty(message = "Email field is empty")
  @field:Email(message = "Invalid email") val email: String,
  @field:NotEmpty(message = "Password field is empty") val password: String,
  @field:NotEmpty(message = "Zip Code field is empty") val zipCode: String,
  @field:NotEmpty(message = "Street field is empty") val street: String,
) {
  fun toEntity(): Customer = Customer(
    firstName = this.firstName,
    lastName = this.lastName,
    cpf = this.cpf,
    income = this.income,
    email = this.email,
    password = this.password,
    address = Address(
      zipCode = this.zipCode,
      street = this.street
    )
  )
}