package leoleite.creditapplication.system.controller.dto

import leoleite.creditapplication.system.entity.Credit
import leoleite.creditapplication.system.entity.Customer
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
  val creditValue: BigDecimal,
  val dayFirstOfInstallment: LocalDate,
  val numberOfInstallments: Int,
  val customerId: Long
) {
  fun toEntity(): Credit = Credit(
    creditValue = this.creditValue,
    dayFirstInstallment = this.dayFirstOfInstallment,
    numberOfInstallment =  this.numberOfInstallments,
    customer = Customer(id = this.customerId)
  )
}