package leoleite.creditapplication.system.entity

import jakarta.persistence.*
import leoleite.creditapplication.system.enumeration.Status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Entity
// @Table(name = "Credito")

data class Credit (
    @Column(nullable = false, unique = true) var creditCode: UUID = UUID.randomUUID(),
    @Column(nullable = false) val creditValue: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false) val dayFirstInstallment: LocalDate,
    @Column(nullable = false) val numberOfInstallment: Int = 0,
    @Enumerated val status: Status = Status.IN_PROGRESS,
    @ManyToOne var customer: Customer? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
)

