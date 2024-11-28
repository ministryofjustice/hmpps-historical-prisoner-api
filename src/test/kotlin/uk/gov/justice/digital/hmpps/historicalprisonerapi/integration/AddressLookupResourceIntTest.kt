package uk.gov.justice.digital.hmpps.historicalprisonerapi.integration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AddressLookupResourceIntTest : IntegrationTestBase() {

  @Nested
  @DisplayName("GET /address-lookup")
  inner class AddressLookupEndpoint {

    @Test
    fun `should return unauthorized if no token`() {
      webTestClient.get()
        .uri("/address-lookup")
        .exchange()
        .expectStatus()
        .isUnauthorized
    }

    @Test
    fun `should return forbidden if no role`() {
      webTestClient.get()
        .uri("/address-lookup")
        .headers(setAuthorisation())
        .exchange()
        .expectStatus()
        .isForbidden
    }

    @Test
    fun `should return forbidden if wrong role`() {
      webTestClient.get()
        .uri("/address-lookup")
        .headers(setAuthorisation(roles = listOf("ROLE_WRONG")))
        .exchange()
        .expectStatus()
        .isForbidden
    }

    @Test
    fun `should return OK`() {
      webTestClient.get()
        .uri("/address-lookup?addressTerms=John,Smith")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content.[*].prisonNumber").value<List<String>> {
          assertThat(it).containsExactlyInAnyOrder("AB111111", "AB111112", "BF123451", "BF123452", "BF123453", "BF123454")
        }
    }
  }
}
