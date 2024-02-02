package leoleite.creditapplication.system.controller

import jakarta.validation.Valid
import leoleite.creditapplication.system.controller.dto.CustomerDto
import leoleite.creditapplication.system.controller.dto.CustomerUpdateDto
import leoleite.creditapplication.system.controller.dto.CustomerView
import leoleite.creditapplication.system.entity.Customer
import leoleite.creditapplication.system.service.implementation.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CustomerController(
  private val customerService: CustomerService
) {
  @PostMapping
  fun saveCustomer(@RequestBody @Valid customerDto: CustomerDto): ResponseEntity<String> {
    val savedCustomer = this.customerService.save(customerDto.toEntity())
    return ResponseEntity.status(HttpStatus.CREATED).body("Customer ${savedCustomer.email} registred!")
  }

  @GetMapping("/{id}")
  fun findById(@PathVariable id: Long): ResponseEntity<CustomerView> {
    val customer: Customer = this.customerService.findById(id)
    return ResponseEntity.status(HttpStatus.OK).body(CustomerView(customer))
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun deleteCustomer(@PathVariable id: Long) = this.customerService.delete(id)

  @PatchMapping
  fun updadeCustomer(
    @RequestParam(value = "customerId") id: Long,
    @RequestBody @Valid customerUpdateDto: CustomerUpdateDto
  ): ResponseEntity<CustomerView> {
    val customer: Customer = this.customerService.findById(id)
    val customerToUpdate: Customer = customerUpdateDto.toEntity(customer)
    val customerUpdated: Customer = this.customerService.save(customerToUpdate)
    return ResponseEntity.status(HttpStatus.OK).body(CustomerView(customerUpdated))
  }
}