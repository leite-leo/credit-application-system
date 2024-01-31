package leoleite.creditapplication.system.controller.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import leoleite.creditapplication.system.entity.Customer
import java.math.BigDecimal

data class CustomerUpdateDto(
  @field:NotEmpty(message = "First Name field is empty") val firstName: String,
  @field:NotEmpty(message = "Last Name field is empty") val lastName: String,
  @field:NotNull(message = "Income field is empty") val income: BigDecimal,
  @field:NotEmpty(message = "Zip Code field is empty") val zipCode: String,
  @field:NotEmpty(message = "Street field is empty") val street: String,
) {
  fun toEntity(customer: Customer): Customer {
    customer.firstName = this.firstName
    customer.lastName = this.lastName
    customer.income = this.income
    customer.address.street = this.street
    customer.address.zipCode = this.zipCode
    return customer
  }
}