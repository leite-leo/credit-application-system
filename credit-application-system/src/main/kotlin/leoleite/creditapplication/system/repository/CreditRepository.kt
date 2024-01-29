package leoleite.creditapplication.system.repository

import leoleite.creditapplication.system.entity.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository

interface CreditRepository: JpaRepository<Credit, Long>