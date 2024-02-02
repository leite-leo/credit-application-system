package leoleite.creditapplication.system.service.implementation

import leoleite.creditapplication.system.entity.Customer
import leoleite.creditapplication.system.exceptions.BussinessException
import leoleite.creditapplication.system.repository.CustomerRepository
import leoleite.creditapplication.system.service.ICustomerService
import org.springframework.stereotype.Service

@Service
class CustomerService(
  private val customerRepository: CustomerRepository
) : ICustomerService {
  override fun save(customer: Customer): Customer =
    this.customerRepository.save(customer)


  override fun findById(id: Long): Customer = this.customerRepository.findById(id).orElseThrow {
    throw BussinessException("Id $id not found!")
  }


  override fun delete(id: Long) {
    val customer: Customer = this.findById(id)
    this.customerRepository.delete(customer)
  }
}