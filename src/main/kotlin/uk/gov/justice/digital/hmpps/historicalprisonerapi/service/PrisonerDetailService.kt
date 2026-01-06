package uk.gov.justice.digital.hmpps.historicalprisonerapi.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import tools.jackson.databind.ObjectMapper
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.PrisonerDetailDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.PrisonerDetailModel
import uk.gov.justice.digital.hmpps.historicalprisonerapi.repository.PrisonerDetailRepository

@Service
class PrisonerDetailService(
  private val prisonerDetailRepository: PrisonerDetailRepository,
  private val objectMapper: ObjectMapper,
) {

  fun getPrisonerDetail(prisonNumber: String): PrisonerDetailDto = prisonerDetailRepository.findByIdOrNull(prisonNumber)?.run {
    objectMapper.readValue(
      """{
        "prisonNumber": "$pkPrisonNumber",
        "personalDetails": $personalDetails,
        "aliases": $aliases,
        "hdcInfo": $hdcInfo,
        "hdcRecall": $hdcRecall,
        "addresses": $addresses,
        "category": $category,
        "courtHearings": $courtHearings,
        "movements": $movements,
        "offences": $offences,
        "adjudications": $adjudications,
        "sentencing": $sentencing
      }""",
      PrisonerDetailModel::class.java,
    ).toDto()
  } ?: throw EntityNotFoundException("Prisoner $prisonNumber not found")
}
