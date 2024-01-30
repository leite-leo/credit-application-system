package leoleite.creditapplication.system.controller.dto

import leoleite.creditapplication.system.entity.Credit
import leoleite.creditapplication.system.enumeration.Status
import java.math.BigDecimal
import java.util.UUID

data class CreditView(
  val creditCode: UUID,
  val creditValue: BigDecimal,
  val numberOfInstallments: Int,
  val status : Status,
  val emailCustomer: String?,
  val incomeCustomer: BigDecimal?
){
  constructor(credit: Credit): this(
    creditCode = credit.creditCode,
    creditValue = credit.creditValue,
    numberOfInstallments = credit.numberOfInstallment,
    status = credit.status,
    emailCustomer = credit.customer?.email,
    incomeCustomer = credit.customer?.income

  )
}