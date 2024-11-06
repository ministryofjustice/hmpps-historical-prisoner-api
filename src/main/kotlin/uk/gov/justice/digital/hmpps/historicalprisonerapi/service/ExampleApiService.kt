package uk.gov.justice.digital.hmpps.historicalprisonerapi.service

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ExampleApiService {
  fun getTime(): LocalDateTime = LocalDateTime.now()
}
