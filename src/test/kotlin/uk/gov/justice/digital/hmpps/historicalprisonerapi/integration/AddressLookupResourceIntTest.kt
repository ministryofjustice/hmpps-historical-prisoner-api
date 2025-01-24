package uk.gov.justice.digital.hmpps.historicalprisonerapi.integration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.Prisoner

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
      testHappyPath("addressTerms=John,Smith") {
        assertThat(it).hasSize(6).extracting("prisonNumber").containsExactlyInAnyOrder("AB111111", "AB111112", "BF123451", "BF123452", "BF123453", "BF123454")
      }
    }

    @Test
    fun `should search by gender`() {
      testHappyPath("addressTerms=John,Smith&gender=f") {
        assertThat(it).hasSize(1).extracting("prisonNumber").containsExactlyInAnyOrder("AB111112")
      }
    }

    @Test
    fun `should ignore gender if blank`() {
      testHappyPath("addressTerms=John,Smith&gender= ") {
        assertThat(it).hasSize(6).extracting("prisonNumber").containsExactlyInAnyOrder("AB111111", "AB111112", "BF123451", "BF123452", "BF123453", "BF123454")
      }
    }

    @Test
    fun `should search by hdc`() {
      testHappyPath("addressTerms=John,Smith&hdc=true") {
        assertThat(it).hasSize(4).extracting("prisonNumber").containsExactlyInAnyOrder("AB111111", "AB111112", "BF123451", "BF123452")
      }
    }

    @Test
    fun `should search by lifer`() {
      testHappyPath("addressTerms=John,Smith&lifer=true") {
        assertThat(it).hasSize(1).extracting("prisonNumber").containsExactlyInAnyOrder("AB111111")
      }
    }

    private fun testHappyPath(parameters: String, function: (t: List<Prisoner>) -> Unit): WebTestClient.BodyContentSpec = webTestClient.get()
      .uri("/address-lookup?$parameters")
      .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.content").value(function)
  }
}
