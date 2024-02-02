package leoleite.creditapplication.system.exceptions

data class BussinessException(override val message: String?): RuntimeException(message)
