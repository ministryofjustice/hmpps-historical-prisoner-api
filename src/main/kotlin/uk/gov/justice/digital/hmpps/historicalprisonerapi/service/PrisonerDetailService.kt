package uk.gov.justice.digital.hmpps.historicalprisonerapi.service

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.historicalprisonerapi.repository.PrisonerDetailRepository

@Service
class PrisonerDetailService(
  private val prisonerDetailRepository: PrisonerDetailRepository,
  private val objectMapper: ObjectMapper,
) {

  fun getPrisonerDetail(prisonNumber: String): String =
    prisonerDetailRepository.findByIdOrNull(prisonNumber)?.run {
      """{
        "prisonNumber": "$pkPrisonNumber",
        "personalDetails": $personalDetails,
        "aliases": $aliases,
        "hdcInfo": $hdcInfo,
        "hdcRecall": $hdcRecall,
        "addresses": $addresses,
        "adjudications": $adjudications,
        "category": $category,
        "courtHearings": $courtHearings,
        "movements": $movements,
        "offences": $offences,
        "offencesInCustody": $offencesInCustody,
        "sentencing": $sentencing
      }"""
    } ?: throw EntityNotFoundException("Prisoner $prisonNumber not found")
}
