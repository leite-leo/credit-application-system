package leoleite.creditapplication.system.controller

import leoleite.creditapplication.system.controller.dto.CustomerDto
import leoleite.creditapplication.system.controller.dto.CustomerView
import leoleite.creditapplication.system.entity.Customer
import leoleite.creditapplication.system.service.implementation.CustomerService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CustomerController(
  private val customerService: CustomerService
) {
  @PostMapping
  fun saveCustomer(@RequestBody customerDto: CustomerDto): String {
    val savedCustomer = this.customerService.save(customerDto.toEntity())
    return "Customer ${savedCustomer.email} registred!"
  }

  @GetMapping("/{id}")
  fun findById(@PathVariable id: Long): CustomerView {
    val customer: Customer = this.customerService.findById(id)
    return CustomerView(customer)
  }

  @DeleteMapping("/{id}")
  fun deleteCustomer(@PathVariable id: Long) = this.customerService.delete(id)
}