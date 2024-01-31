package leoleite.creditapplication.system.controller.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import leoleite.creditapplication.system.entity.Credit
import leoleite.creditapplication.system.entity.Customer
import org.springframework.format.annotation.NumberFormat
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
  @field:NotNull(message = "Credit Value field is empty") val creditValue: BigDecimal,
  @field:NotEmpty(message = "First installment date field is empty")
  @field:Future val dayFirstOfInstallment: LocalDate,
  @field:NotEmpty(message = "Number of installments field is empty")
  @field:NumberFormat val numberOfInstallments: Int,
  @field:NotNull(message = "Custumer Id field is empty")val customerId: Long
) {
  fun toEntity(): Credit = Credit(
    creditValue = this.creditValue,
    dayFirstInstallment = this.dayFirstOfInstallment,
    numberOfInstallment =  this.numberOfInstallments,
    customer = Customer(id = this.customerId)
  )
}