package uk.gov.justice.digital.hmpps.historicalprisonerapi.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.PrisonerSearchDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.repository.PrisonerRepository

class AddressLookupServiceTest {
  private val prisonerRepository: PrisonerRepository = mock()
  private val addressLookupService = AddressLookupService(prisonerRepository)
  private val pageRequest = PageRequest.ofSize(5)
  private val expectedPrisoners =
    PageImpl<PrisonerSearchDto>(listOf(PrisonerSearchImplDto("AB111111"), PrisonerSearchImplDto("AB111112")))

  @Test
  fun `should find prisoners with addresses`() {
    val addressTerms = "John Smith"

    whenever(prisonerRepository.findByAddresses(anyString(), anyString(), anyBoolean(), anyBoolean(), any())).thenReturn(expectedPrisoners)

    val foundPrisoners = addressLookupService.findPrisonersWithAddresses(
      addressTerms = addressTerms,
      gender = "Y",
      hdc = false,
      lifer = true,
      pageRequest = pageRequest,
    )
    assertThat(foundPrisoners).containsExactlyInAnyOrderElementsOf(expectedPrisoners)

    verify(prisonerRepository).findByAddresses(
      addressTerms = "NEAR((John, Smith), 5, TRUE)",
      gender = "Y",
      hdc = false,
      lifer = true,
      pageRequest = pageRequest,
    )
  }

  @Test
  fun `should remove unnecessary characters before calling repository`() {
    val addressTerms = "John  Smith and .,  John's friend"

    whenever(prisonerRepository.findByAddresses(anyString(), anyString(), anyBoolean(), anyBoolean(), any())).thenReturn(expectedPrisoners)

    val foundPrisoners = addressLookupService.findPrisonersWithAddresses(
      addressTerms = addressTerms,
      gender = "Y",
      hdc = false,
      lifer = true,
      pageRequest = pageRequest,
    )
    assertThat(foundPrisoners).containsExactlyInAnyOrderElementsOf(expectedPrisoners)

    verify(prisonerRepository).findByAddresses(
      addressTerms = "NEAR((John, Smith, and, Johns, friend), 5, TRUE)",
      gender = "Y",
      hdc = false,
      lifer = true,
      pageRequest = pageRequest,
    )
  }

  @Test
  fun `should trim at start and end as well before calling repository`() {
    val addressTerms = "  John  Smith and John's friend   "

    whenever(prisonerRepository.findByAddresses(anyString(), anyString(), anyBoolean(), anyBoolean(), any())).thenReturn(expectedPrisoners)

    val foundPrisoners = addressLookupService.findPrisonersWithAddresses(
      addressTerms = addressTerms,
      gender = "Y",
      hdc = false,
      lifer = true,
      pageRequest = pageRequest,
    )
    assertThat(foundPrisoners).containsExactlyInAnyOrderElementsOf(expectedPrisoners)

    verify(prisonerRepository).findByAddresses(
      addressTerms = "NEAR((John, Smith, and, Johns, friend), 5, TRUE)",
      gender = "Y",
      hdc = false,
      lifer = true,
      pageRequest = pageRequest,
    )
  }
}
