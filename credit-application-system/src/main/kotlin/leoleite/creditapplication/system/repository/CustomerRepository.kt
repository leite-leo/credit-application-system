package leoleite.creditapplication.system.repository

import leoleite.creditapplication.system.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository

interface CustomerRepository: JpaRepository<Customer, Long>