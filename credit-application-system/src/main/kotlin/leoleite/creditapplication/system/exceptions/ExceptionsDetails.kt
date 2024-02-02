package leoleite.creditapplication.system.exceptions

import java.time.LocalDate
import java.time.LocalDateTime

data class ExceptionsDetails(
  val title: String,
  val timestamp: LocalDateTime,
  val status: Int,
  val exception: String,
  val details: MutableMap<String, String?>
)
