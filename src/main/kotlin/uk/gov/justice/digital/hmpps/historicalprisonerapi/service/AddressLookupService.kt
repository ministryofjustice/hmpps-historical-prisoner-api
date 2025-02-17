package uk.gov.justice.digital.hmpps.historicalprisonerapi.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.PrisonerSearchDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.repository.PrisonerRepository

@Service
class AddressLookupService(private val prisonerRepository: PrisonerRepository) {
  fun findPrisonersWithAddresses(
    addressTerms: String,
    gender: String?,
    hdc: Boolean?,
    lifer: Boolean?,
    pageRequest: Pageable,
  ): Page<PrisonerSearchDto> {
    // duplicate logic from HPA front end
    val searchTerm = addressTerms
      .replace(".", " ")
      .replace(",", " ")
      .replace("'", "")
      .trim()
      .split("\\s+".toRegex())
      .joinToString(", ") { it.trim() }
    return prisonerRepository.findByAddresses(
      "NEAR(($searchTerm), 5, TRUE)",
      gender = gender?.uppercaseTrimToNull(),
      hdc = hdc,
      lifer = lifer,
      pageRequest,
    )
  }

  private fun String.uppercaseTrimToNull() = this.uppercase().trim().ifBlank { null }
}
