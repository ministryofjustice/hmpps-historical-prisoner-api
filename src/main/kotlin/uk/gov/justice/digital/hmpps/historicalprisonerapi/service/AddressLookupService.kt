package uk.gov.justice.digital.hmpps.historicalprisonerapi.service

import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.historicalprisonerapi.repository.AddressLookupRepository

@Service
class AddressLookupService(private val addressLookupRepository: AddressLookupRepository) {
  fun findPrisonersWithAddresses(addressTerms: String): List<String> {
    // duplicate logic from HPA front end
    val searchTerm = addressTerms
      .replace(".", " ")
      .replace(",", " ")
      .replace("'", "")
      .trim()
      .split("\\s+".toRegex())
      .joinToString(", ") { it.trim() }
    return addressLookupRepository.findPrisonersWithAddresses("NEAR(($searchTerm), 5, TRUE)")
  }
}
