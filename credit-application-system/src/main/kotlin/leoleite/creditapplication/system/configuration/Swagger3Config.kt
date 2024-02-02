package leoleite.creditapplication.system.configuration

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(info = Info(title = "Credit Application System API - Documentation"))
@Configuration
class Swagger3Config {
  @Bean
  fun publicApi(): GroupedOpenApi? {
    return GroupedOpenApi.builder()
      .group("springcreditapplicationsystem-public")
      .pathsToMatch("/api/customers/**", "/api/credits/**")
      .build()
  }
}