package leoleite.creditapplication.system.controller

import leoleite.creditapplication.system.controller.dto.CreditDto
import leoleite.creditapplication.system.controller.dto.CreditViewList
import leoleite.creditapplication.system.entity.Credit
import leoleite.creditapplication.system.service.implementation.CreditService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/credits")
class CreditController(
  private val creditService: CreditService
) {
  @PostMapping
  fun savecredit(@RequestBody creditDto: CreditDto): String {
    val credit: Credit = this.creditService.save(creditDto.toEntity())
    return "Credit ${credit.creditCode} - Customer ${credit.customer?.firstName} registred!"
  }

  @GetMapping
  fun findAllByCustomerId(@RequestParam(value ="customerId") customerId: Long): List<CreditViewList> {
    return this.creditService.findAllByCustomer(customerId)
      .stream().map { credit: Credit -> CreditViewList(credit) }
      .collect(Collectors.toList())

  }
}