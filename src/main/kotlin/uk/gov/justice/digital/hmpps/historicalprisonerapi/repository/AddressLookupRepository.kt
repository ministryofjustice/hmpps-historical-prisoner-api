package uk.gov.justice.digital.hmpps.historicalprisonerapi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.AddressLookup

@Repository
interface AddressLookupRepository : JpaRepository<AddressLookup, Int> {
  @Query(nativeQuery = true, value = "SELECT DISTINCT PRISON_NUMBER FROM HPA.ADDRESS_LOOKUP WHERE CONTAINS(ADDRESS_TEXT, :addressTerms)")
  fun findPrisonersWithAddresses(@Param("addressTerms") addressTerms: String): List<String>
}
