package uk.gov.justice.digital.hmpps.historicalprisonerapi.integration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.Prisoner

class IndentifierSearchResourceIntTest : IntegrationTestBase() {

  @Nested
  @DisplayName("GET /identifiers")
  inner class IdentifiersEndpoint {

    @Test
    fun `should return unauthorized if no token`() {
      webTestClient.get()
        .uri("/identifiers")
        .exchange()
        .expectStatus()
        .isUnauthorized
    }

    @Test
    fun `should return forbidden if no role`() {
      webTestClient.get()
        .uri("/identifiers")
        .headers(setAuthorisation())
        .exchange()
        .expectStatus()
        .isForbidden
    }

    @Test
    fun `should return forbidden if wrong role`() {
      webTestClient.get()
        .uri("/identifiers")
        .headers(setAuthorisation(roles = listOf("ROLE_WRONG")))
        .exchange()
        .expectStatus()
        .isForbidden
    }

    @Test
    fun `should search by prison number`() {
      webTestClient.get()
        .uri("/identifiers?prisonNumber=AB111111")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(3).extracting("prisonNumber").containsOnly("AB111111")
        }
    }

    @Test
    fun `should search by pnc number`() {
      webTestClient.get()
        .uri("/identifiers?pnc=012345/99D")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(4).extracting("prisonNumber").containsOnly("AB111112")
        }
    }

    @Test
    fun `should search by cro number`() {
      webTestClient.get()
        .uri("/identifiers?cro=BK3333/CR0")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(1).extracting("prisonNumber").containsOnly("BF123454")
        }
    }

    @Test
    fun `should search by all identifiers`() {
      webTestClient.get()
        .uri("/identifiers?pnc=BK2345/BK1&cro=BK1111/CR0&prisonNumber=BF123459")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(5).extracting("prisonNumber").containsOnly("BF123459")
        }
    }

    @Test
    fun `should return bad request when no identifiers provided`() {
      webTestClient.get()
        .uri("/identifiers")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isBadRequest
    }

    @Test
    fun `should return bad request when identifiers all blank`() {
      webTestClient.get()
        .uri("/identifiers?prisonNumber= &pnc= &cro= ")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isBadRequest
    }
  }
}
