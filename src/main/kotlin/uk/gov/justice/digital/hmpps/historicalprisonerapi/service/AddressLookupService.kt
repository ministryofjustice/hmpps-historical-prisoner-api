package uk.gov.justice.digital.hmpps.historicalprisonerapi.service

import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.historicalprisonerapi.repository.AddressLookupRepository

@Service
class AddressLookupService(private val addressLookupRepository: AddressLookupRepository) {
  fun findPrisonersWithAddresses(addressTerms: String): List<String> =
    addressLookupRepository.findPrisonersWithAddresses("NEAR(($addressTerms), 5, TRUE)")
}
