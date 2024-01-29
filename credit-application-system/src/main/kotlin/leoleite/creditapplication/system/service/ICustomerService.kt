package leoleite.creditapplication.system.service

import leoleite.creditapplication.system.entity.Customer

interface ICustomerService {
    fun save(customer: Customer): Customer
    fun findById(id: Long): Customer
    fun delete(id: Long)
}