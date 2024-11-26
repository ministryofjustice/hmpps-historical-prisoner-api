package uk.gov.justice.digital.hmpps.historicalprisonerapi.integration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.Prisoner
import java.time.LocalDate

class PrisonerSearchResourceIntTest : IntegrationTestBase() {

  @Nested
  @DisplayName("GET /search")
  inner class SearchEndpoint {

    @Test
    fun `should return unauthorized if no token`() {
      webTestClient.get()
        .uri("/search")
        .exchange()
        .expectStatus()
        .isUnauthorized
    }

    @Test
    fun `should return forbidden if no role`() {
      webTestClient.get()
        .uri("/search")
        .headers(setAuthorisation())
        .exchange()
        .expectStatus()
        .isForbidden
    }

    @Test
    fun `should return forbidden if wrong role`() {
      webTestClient.get()
        .uri("/search")
        .headers(setAuthorisation(roles = listOf("ROLE_WRONG")))
        .exchange()
        .expectStatus()
        .isForbidden
    }

    @Test
    fun `should search by forename`() {
      webTestClient.get()
        .uri("/search?forename=George")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(3).extracting("prisonNumber").containsOnly("BF123451", "BF123454", "BF123459")
        }
    }

    @Test
    fun `should search by forename with wildcard`() {
      webTestClient.get()
        .uri("/search?forename=Geor%")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(3).extracting("prisonNumber").containsOnly("BF123451", "BF123454", "BF123459")
        }
    }

    @Test
    fun `should search by forename with initial`() {
      webTestClient.get()
        .uri("/search?forename=G")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(4).extracting("prisonNumber").containsOnly("BF123451", "BF123454", "BF123455", "BF123459")
        }
    }

    @Test
    fun `should search by surname`() {
      webTestClient.get()
        .uri("/search?surname=wilson")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(2).extracting("prisonNumber").containsOnly("BF123455")
        }
    }

    @Test
    fun `should search by surname with wildcard`() {
      webTestClient.get()
        .uri("/search?surname=wils%")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(2).extracting("prisonNumber").containsOnly("BF123455")
        }
    }

    @Test
    fun `should search by date of birth`() {
      webTestClient.get()
        .uri("/search?dateOfBirth=1967-01-01")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(4).extracting("prisonNumber").containsOnly("BF123451", "BF123459")
        }
    }

    @Test
    fun `should search by age with one term`() {
      val age = LocalDate.now().year - LocalDate.parse("1987-01-01").year
      webTestClient.get()
        .uri("/search?ageFrom=$age")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(8).extracting("prisonNumber").containsOnly("DD000001", "DD000003", "DD000004", "DD000005", "DD000007", "DD000008", "DD000010", "DD000012")
        }
    }

    @Test
    fun `should search by age with range`() {
      val age = LocalDate.now().year - LocalDate.parse("1955-01-01").year
      webTestClient.get()
        .uri("/search?ageFrom=$age&ageTo=${age + 2}")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(2).extracting("prisonNumber").containsOnly("BF123451", "BF123459")
        }
    }

    @Test
    fun `should search by gender`() {
      webTestClient.get()
        .uri("/search?forename=f&gender=f")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(4).extracting("prisonNumber").containsOnly("AB111112", "AB111114", "AB111116", "AB111118")
        }
    }

    @Test
    fun `should search by hdc`() {
      webTestClient.get()
        .uri("/search?forename=f&hdc=true")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(2).extracting("prisonNumber").containsOnly("AB111111", "AB111112")
        }
    }

    @Test
    fun `should search by lifer`() {
      webTestClient.get()
        .uri("/search?forename=f&lifer=true")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(1).extracting("prisonNumber").containsOnly("AB111111")
        }
    }

    @Test
    fun `should search by all terms`() {
      val age = LocalDate.now().year - LocalDate.parse("1980-01-01").year
      webTestClient.get()
        .uri("/search?surname=Surn%&forename=F&dateOfBirth=1980-01-01&ageFrom=$age&ageTo=$age&gender=M&hdc=true&lifer=true")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content").value<List<Prisoner>> {
          assertThat(it).hasSize(1).extracting("prisonNumber").containsOnly("AB111111")
        }
    }

    @Test
    fun `should return bad request when no primary terms provided`() {
      webTestClient.get()
        .uri("/search")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isBadRequest
    }

    @Test
    fun `should return bad request when terms all blank`() {
      webTestClient.get()
        .uri("/search?forename= &surname= ")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isBadRequest
    }

    @Test
    fun `should only return specific fields`() {
      webTestClient.get()
        .uri("/search?forename=f&hdc=true")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content.[0].prisonNumber").isEqualTo("AB111111")
        .jsonPath("$.content.[0].receptionDate").isEqualTo("1999-01-01")
        .jsonPath("$.content.[0].primarySurname").isEqualTo("SURNAMEA")
        .jsonPath("$.content.[0].primaryForename1").isEqualTo("FIRSTA")
        .jsonPath("$.content.[0].primaryForename2").isEqualTo("MIDDLEA")
        .jsonPath("$.content.[0].primaryBirthDate").isEqualTo("1980-01-01")
        .jsonPath("$.content.[0].isAlias").isEqualTo("false")
        .jsonPath("$.content.[0].surname").isEqualTo("SURNAMEA")
        .jsonPath("$.content.[0].forename1").isEqualTo("FIRSTA")
        .jsonPath("$.content.[0].forename2").isEqualTo("MIDDLEA")
    }

    @Test
    fun `should not return ignored fields`() {
      webTestClient.get()
        .uri("/search?forename=f&hdc=true")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.content.[0].id").doesNotExist()
        .jsonPath("$.content.[0].personIdentifier").doesNotExist()
        .jsonPath("$.content.[0].birthDate").doesNotExist()
        .jsonPath("$.content.[0].sex").doesNotExist()
        .jsonPath("$.content.[0].pncNumber").doesNotExist()
        .jsonPath("$.content.[0].croNumber").doesNotExist()
        .jsonPath("$.content.[0].hasHdc").doesNotExist()
        .jsonPath("$.content.[0].isLifer").doesNotExist()
    }
  }
}
