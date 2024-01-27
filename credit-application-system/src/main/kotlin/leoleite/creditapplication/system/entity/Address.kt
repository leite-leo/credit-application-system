package leoleite.creditapplication.system.entity

import jakarta.persistence.*

@Embeddable

data class Address (
    @Column(nullable = false) var zipCode: String = "",
    @Column(nullable = false) var street: String = "",
)
