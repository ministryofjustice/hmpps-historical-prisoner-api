package uk.gov.justice.digital.hmpps.historicalprisonerapi.integration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient
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
      testHappyPath("forename=George") {
        assertThat(it).hasSize(3).extracting("prisonNumber").containsOnly("BF123451", "BF123454", "BF123459")
      }
    }

    @Test
    fun `should search by forename in uppercase and trimmed`() {
      testHappyPath("forename= george  ") {
        assertThat(it).hasSize(3).extracting("prisonNumber").containsOnly("BF123451", "BF123454", "BF123459")
      }
    }

    @Test
    fun `should search by forename with wildcard`() {
      testHappyPath("forename=Geor%") {
        assertThat(it).hasSize(3).extracting("prisonNumber").containsOnly("BF123451", "BF123454", "BF123459")
      }
    }

    @Test
    fun `should search by forename with initial`() {
      testHappyPath("forename=G") {
        assertThat(it).hasSize(4).extracting("prisonNumber").containsOnly("BF123451", "BF123454", "BF123455", "BF123459")
      }
    }

    @Test
    fun `should search by surname`() {
      testHappyPath("surname=wilson") {
        assertThat(it).hasSize(2).extracting("prisonNumber").containsOnly("BF123455")
      }
    }

    @Test
    fun `should search by surname in uppercase and trimmed`() {
      testHappyPath("surname= wilson  ") {
        assertThat(it).hasSize(2).extracting("prisonNumber").containsOnly("BF123455")
      }
    }

    @Test
    fun `should search by surname with wildcard`() {
      testHappyPath("surname=wils%") {
        assertThat(it).hasSize(2).extracting("prisonNumber").containsOnly("BF123455")
      }
    }

    @Test
    fun `should search by date of birth`() {
      testHappyPath("dateOfBirth=1967-01-01") {
        assertThat(it).hasSize(4).extracting("prisonNumber").containsOnly("BF123451", "BF123459")
      }
    }

    @Test
    fun `should ignore forename and surname in search if blank`() {
      testHappyPath("dateOfBirth=1967-01-01&forename= &surname= ") {
        assertThat(it).hasSize(4).extracting("prisonNumber").containsOnly("BF123451", "BF123459")
      }
    }

    @Test
    fun `should ignore forename and surname in search if empty`() {
      testHappyPath("dateOfBirth=1967-01-01&forename=&surname=") {
        assertThat(it).hasSize(4).extracting("prisonNumber").containsOnly("BF123451", "BF123459")
      }
    }

    @Test
    fun `should search by age with one term`() {
      val age = LocalDate.now().year - LocalDate.parse("1987-01-01").year
      testHappyPath("ageFrom=$age") {
        assertThat(it).hasSize(8).extracting("prisonNumber").containsOnly("DD000001", "DD000003", "DD000004", "DD000005", "DD000007", "DD000008", "DD000010", "DD000012")
      }
    }

    @Test
    fun `should search by age with range`() {
      val age = LocalDate.now().year - LocalDate.parse("1955-01-01").year
      testHappyPath("ageFrom=$age&ageTo=${age + 2}") {
        assertThat(it).hasSize(2).extracting("prisonNumber").containsOnly("BF123451", "BF123459")
      }
    }

    @Test
    fun `should search by gender`() {
      testHappyPath("forename=f&gender=f") {
        assertThat(it).hasSize(4).extracting("prisonNumber").containsOnly("AB111112", "AB111114", "AB111116", "AB111118")
      }
    }

    @Test
    fun `should ignore gender if blank`() {
      testHappyPath("forename=firsta&gender= ") {
        assertThat(it).hasSize(1).extracting("prisonNumber").containsOnly("AB111111")
      }
    }

    @Test
    fun `should search by hdc`() {
      testHappyPath("forename=f&hdc=true") {
        assertThat(it).hasSize(2).extracting("prisonNumber").containsOnly("AB111111", "AB111112")
      }
    }

    @Test
    fun `should search by lifer`() {
      testHappyPath("forename=f&lifer=true") {
        assertThat(it).hasSize(1).extracting("prisonNumber").containsOnly("AB111111")
      }
    }

    @Test
    fun `should search by all terms`() {
      val age = LocalDate.now().year - LocalDate.parse("1980-01-01").year
      testHappyPath("surname=Surn%&forename=F&dateOfBirth=1980-01-01&ageFrom=$age&ageTo=$age&gender=M&hdc=true&lifer=true") {
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

  private fun testHappyPath(parameters: String, function: (t: List<Prisoner>) -> Unit): WebTestClient.BodyContentSpec =
    webTestClient.get()
      .uri("/search?$parameters")
      .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.content").value(function)
}
