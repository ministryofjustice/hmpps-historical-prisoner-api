package uk.gov.justice.digital.hmpps.historicalprisonerapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
class HmppsHistoricalPrisonerApi

fun main(args: Array<String>) {
  runApplication<HmppsHistoricalPrisonerApi>(*args)
}
