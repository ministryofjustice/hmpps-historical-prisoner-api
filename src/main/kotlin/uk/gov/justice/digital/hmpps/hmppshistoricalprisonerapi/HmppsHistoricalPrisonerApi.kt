package uk.gov.justice.digital.hmpps.hmppshistoricalprisonerapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRepositoriesAutoConfiguration
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(
  exclude = [
    ReactiveElasticsearchRestClientAutoConfiguration::class,
    ReactiveElasticsearchRepositoriesAutoConfiguration::class
  ]
)
@ConfigurationPropertiesScan
class HmppsHistoricalPrisonerApi

fun main(args: Array<String>) {
  runApplication<HmppsHistoricalPrisonerApi>(*args)
}
