package leoleite.creditapplication.system.service.implementation

import leoleite.creditapplication.system.entity.Credit
import leoleite.creditapplication.system.exceptions.BussinessException
import leoleite.creditapplication.system.repository.CreditRepository
import leoleite.creditapplication.system.service.ICreditService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditService(
  private val creditRepository: CreditRepository,
  private val customerService: CustomerService
) : ICreditService {
  override fun save(credit: Credit): Credit {
    credit.apply {
      customer = customerService.findById(credit.customer?.id!!)
    }
    return this.creditRepository.save(credit)
  }

  override fun findAllByCustomer(customerId: Long): List<Credit> =
    this.creditRepository.findAllByCustomerId(customerId)


  override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
    val credit: Credit = this.creditRepository.findByCreditCode(creditCode)
      ?: throw BussinessException("Credit Code $creditCode not found!")
    return if (credit.customer?.id == customerId) credit else throw IllegalArgumentException("Contact Admin")
//        if (credit.customer?.id == customerId) {
//            return credit
//        } else {
//            throw RuntimeException("Contact Admin")
//        }
  }
}