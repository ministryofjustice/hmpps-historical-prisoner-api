package uk.gov.justice.digital.hmpps.historicalprisonerapi.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import uk.gov.justice.digital.hmpps.historicalprisonerapi.repository.AddressLookupRepository

class AddressLookupServiceTest {
  private val addressLookupRepository: AddressLookupRepository = mock()
  private val addressLookupService = AddressLookupService(addressLookupRepository)

  @Test
  fun `should find prisoners with addresses`() {
    val addressTerms = "John Smith"
    val expectedPrisoners = listOf("AB111111", "AB111112")

    whenever(addressLookupRepository.findPrisonersWithAddresses(anyString())).thenReturn(expectedPrisoners)

    val foundPrisoners = addressLookupService.findPrisonersWithAddresses(addressTerms)
    assertThat(foundPrisoners).containsExactlyInAnyOrderElementsOf(expectedPrisoners)

    verify(addressLookupRepository).findPrisonersWithAddresses("NEAR((John, Smith), 5, TRUE)")
  }

  @Test
  fun `should remove unnecessary characters before calling repository`() {
    val addressTerms = "John  Smith and .,  John's friend"
    val expectedPrisoners = listOf("AB111111", "AB111112")

    whenever(addressLookupRepository.findPrisonersWithAddresses(anyString())).thenReturn(expectedPrisoners)

    val foundPrisoners = addressLookupService.findPrisonersWithAddresses(addressTerms)
    assertThat(foundPrisoners).containsExactlyInAnyOrderElementsOf(expectedPrisoners)

    verify(addressLookupRepository).findPrisonersWithAddresses("NEAR((John, Smith, and, Johns, friend), 5, TRUE)")
  }

  @Test
  fun `should trim at start and end as well before calling repository`() {
    val addressTerms = "  John  Smith and John's friend   "
    val expectedPrisoners = listOf("AB111111", "AB111112")

    whenever(addressLookupRepository.findPrisonersWithAddresses(anyString())).thenReturn(expectedPrisoners)

    val foundPrisoners = addressLookupService.findPrisonersWithAddresses(addressTerms)
    assertThat(foundPrisoners).containsExactlyInAnyOrderElementsOf(expectedPrisoners)

    verify(addressLookupRepository).findPrisonersWithAddresses("NEAR((John, Smith, and, Johns, friend), 5, TRUE)")
  }
}
