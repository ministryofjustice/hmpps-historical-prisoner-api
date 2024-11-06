package uk.gov.justice.digital.hmpps.historicalprisonerapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HmppsHistoricalPrisonerApi

fun main(args: Array<String>) {
  runApplication<HmppsHistoricalPrisonerApi>(*args)
}
