package leoleite.creditapplication.system.controller.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
// import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import leoleite.creditapplication.system.entity.Credit
import leoleite.creditapplication.system.entity.Customer
import org.springframework.format.annotation.NumberFormat
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
  @field:NotNull(message = "Credit Value field is empty") val creditValue: BigDecimal,
  @field:Future val dayFirstOfInstallment: LocalDate,
  @field:Min(value = 1) @field:Max(value = 48)
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