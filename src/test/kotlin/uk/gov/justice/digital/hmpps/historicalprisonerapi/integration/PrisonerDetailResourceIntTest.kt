package uk.gov.justice.digital.hmpps.historicalprisonerapi.integration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.AddressesDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.AdjudicationsDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.AliasesDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.CategoryDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.CourtHearingsDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.HdcInfoDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.HdcRecallDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.MovementsDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.OffencesDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.OffencesInCustodyDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.PersonalDetailsDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.PrisonerDetailDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.PunishmentDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.SentencingDto
import java.time.LocalDate

class PrisonerDetailResourceIntTest : IntegrationTestBase() {

  @Nested
  @DisplayName("GET /detail")
  inner class DetailEndpoint {

    @Test
    fun `should return unauthorized if no token`() {
      webTestClient.get()
        .uri("/detail/AB111111")
        .exchange()
        .expectStatus()
        .isUnauthorized
    }

    @Test
    fun `should return forbidden if no role`() {
      webTestClient.get()
        .uri("/detail/AB111111")
        .headers(setAuthorisation())
        .exchange()
        .expectStatus()
        .isForbidden
    }

    @Test
    fun `should return forbidden if wrong role`() {
      webTestClient.get()
        .uri("/detail/AB111111")
        .headers(setAuthorisation(roles = listOf("ROLE_WRONG")))
        .exchange()
        .expectStatus()
        .isForbidden
    }

    @Test
    fun `should return personal details`() {
      webTestClient.get()
        .uri("/detail/AB111111")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$").value(PrisonerDetailDto::class.java) {
          assertThat(it.prisonNumber).isEqualTo("AB111111")
          assertThat(it.personalDetails).isEqualTo(
            PersonalDetailsDto(
              prisonNumber = "AB111111",
              pncNumber = "012345/99A",
              croNumber = "012345/99C",
              paroleNumbers = "AA12311",
              initial = "F",
              firstName = "FIRSTA",
              middleName = "MIDDLEA",
              lastName = "SURNAMEA",
              sex = "M",
              dob = LocalDate.parse("1980-01-01"),
              ethnicity = "White",
              birthCountry = "England",
              nationality = "United Kingdom",
              religion = "Church of England",
              receptionDate = LocalDate.parse("1999-01-01"),
              maritalStatus = "Single",
            ),
          )
        }
    }

    @Test
    fun `should return aliases`() {
      webTestClient.get()
        .uri("/detail/AB111111")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$").value(PrisonerDetailDto::class.java) {
          assertThat(it.prisonNumber).isEqualTo("AB111111")
          assertThat(it.aliases).containsExactly(
            AliasesDto(last = "ALIASA", first = "OTHERA", middle = "A", birthDate = "19800101"),
            AliasesDto(last = "ALIASB", first = "OTHERB", middle = "B", birthDate = "19800102"),
          )
        }
    }

    @Test
    fun `should return hdc info`() {
      webTestClient.get()
        .uri("/detail/AB111111")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$").value(PrisonerDetailDto::class.java) {
          assertThat(it.prisonNumber).isEqualTo("AB111111")
          assertThat(it.hdcInfo).containsExactly(
            HdcInfoDto(
              stage = "HDC ELIGIBILITY RESULT",
              status = "ELIGIBLE",
              date = LocalDate.parse("2013-03-18"),
              reasons = "CREATED MANUALLY",
            ),
            HdcInfoDto(
              stage = "HDC ELIGIBILITY",
              status = "MANUAL CHECK PASS",
              date = LocalDate.parse("2013-03-18"),
              reasons = "PASS ALL ELIGIBILITY CHECKS",
            ),
            HdcInfoDto(
              stage = "HDC ELIGIBILITY",
              status = "AUTO CHECK PASS",
              date = LocalDate.parse("2012-06-27"),
              reasons = "<14 DAYS FROM HDCED, CHANGE IN SENTENCE HISTORY, HDCED EARLIER THAN 28/01/99, UNDER 18",
            ),
            HdcInfoDto(
              stage = "HDC ELIGIBILITY",
              status = "AUTO CHECK PASS",
              date = LocalDate.parse("2012-06-27"),
              reasons = "<14 DAYS FROM HDCED, MANUAL CHECK - PREV. CUSTODY",
            ),
          )
        }
    }

    @Test
    fun `should return hdc recall`() {
      webTestClient.get()
        .uri("/detail/AB111111")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$").value(PrisonerDetailDto::class.java) {
          assertThat(it.prisonNumber).isEqualTo("AB111111")
          assertThat(it.hdcRecall).containsExactly(
            HdcRecallDto(
              createdDate = LocalDate.parse("2001-01-04"),
              curfewEndDate = LocalDate.parse("2001-01-05"),
              outcomeDate = LocalDate.parse("2001-01-02"),
              outcome = "Re-released following recall",
              reason = "CHANGE OF CIRCS 38A1(b)",
            ),
            HdcRecallDto(
              createdDate = LocalDate.parse("2001-01-01"),
              curfewEndDate = LocalDate.parse("2001-01-02"),
              outcomeDate = LocalDate.parse("2001-01-02"),
              outcome = "Licence revoked: recalled",
              reason = "BREACH CONDITIONS 38A1(a)",
            ),
          )
        }
    }

    @Test
    fun `should return addresses`() {
      webTestClient.get()
        .uri("/detail/AB111111")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$").value(PrisonerDetailDto::class.java) {
          assertThat(it.prisonNumber).isEqualTo("AB111111")
          assertThat(it.addresses).containsExactly(
            AddressesDto(
              type = "Other",
              person = "FIRST LASTA",
              street = "1, Street Road",
              town = "Town A",
              county = "MERSEYSIDE",
              sequence = 8,
            ),
            AddressesDto(
              type = "Unknown",
              person = "",
              street = "",
              town = "Town B",
              county = "MERSEYSIDE",
              sequence = 7,
            ),
            AddressesDto(
              type = "Next of kin",
              person = "FIRST LASTC",
              street = "3, Street Road",
              town = "Town C",
              county = "MERSEYSIDE",
              sequence = 6,
            ),
          )
        }
    }

    @Test
    fun `should return adjudications`() {
      webTestClient.get()
        .uri("/detail/AB111111")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$").value(PrisonerDetailDto::class.java) {
          assertThat(it.prisonNumber).isEqualTo("AB111111")
          assertThat(it.adjudications).containsExactly(
            AdjudicationsDto(
              date = LocalDate.parse("1991-01-04"),
              outcome = "DISMISSED",
              charge = "ASSAULT ON INMATE",
              establishment = "FULL SUTTON",
            ),
            AdjudicationsDto(
              date = LocalDate.parse("1991-01-03"),
              outcome = "NOT PROCEEDED WITH",
              charge = "FIGHTING",
              establishment = "FRANKLAND",
            ),
            AdjudicationsDto(
              date = LocalDate.parse("1991-01-02"),
              outcome = "NOT PROVEN",
              charge = "OFFENCE AGAINST GOAD",
              establishment = "DURHAM",
              punishments = listOf(
                PunishmentDto(
                  punishment = "EXTRA WORK",
                  duration = 21,
                ),
              ),
            ),
            AdjudicationsDto(
              date = LocalDate.parse("1991-01-01"),
              outcome = "PROVED",
              charge = "DISOBEYING A LAWFUL ORDER",
              establishment = "BELMARSH",
              punishments = listOf(
                PunishmentDto(
                  punishment = "CAUTION",
                  duration = 7,
                ),
                PunishmentDto(
                  punishment = "CONFINEMENT TO CELL OR ROOM",
                  duration = 7,
                ),
              ),
            ),
          )
        }
    }

    @Test
    fun `should return category`() {
      webTestClient.get()
        .uri("/detail/AB111111")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$").value(PrisonerDetailDto::class.java) {
          assertThat(it.prisonNumber).isEqualTo("AB111111")
          assertThat(it.category).isEqualTo(
            CategoryDto(
              date = LocalDate.parse("2001-01-02"),
              category = "UNCATEGORISED (SENT MALES)",
            ),
          )
        }
    }

    @Test
    fun `should return court hearings`() {
      webTestClient.get()
        .uri("/detail/AB111111")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$").value(PrisonerDetailDto::class.java) {
          assertThat(it.prisonNumber).isEqualTo("AB111111")
          assertThat(it.courtHearings).containsExactly(
            CourtHearingsDto(
              date = LocalDate.parse("2001-01-12"),
              court = "LISKEARD COUNTY COURT",
            ),
            CourtHearingsDto(
              date = LocalDate.parse("2001-01-04"),
              court = "WELLS COUNTY COURT",
            ),
            CourtHearingsDto(
              date = LocalDate.parse("2001-01-03"),
              court = "THORNBURY COUNTY COURT",
            ),
            CourtHearingsDto(
              date = LocalDate.parse("2001-01-02"),
              court = "LISKEARD COUNTY COURT",
            ),
          )
        }
    }

    @Test
    fun `should return movements`() {
      webTestClient.get()
        .uri("/detail/AB111111")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$").value(PrisonerDetailDto::class.java) {
          assertThat(it.prisonNumber).isEqualTo("AB111111")
          assertThat(it.movements).containsExactly(
            MovementsDto(
              date = LocalDate.parse("1988-02-12"),
              type = "D",
              movement = "DISCHARGED TO COURT",
              establishment = "FRANKLAND",
            ),
            MovementsDto(
              date = LocalDate.parse("1987-12-21"),
              type = "R",
              movement = "UNCONVICTED REMAND",
              establishment = "DURHAM",
            ),
            MovementsDto(
              date = LocalDate.parse("1987-12-21"),
              type = "D",
              movement = "DISCHARGED TO COURT",
              establishment = "BELMARSH",
            ),
            MovementsDto(
              date = LocalDate.parse("1987-09-28"),
              type = "R",
              movement = "UNCONVICTED REMAND",
              establishment = "BELMARSH",
            ),
          )
        }
    }

    @Test
    fun `should return offences`() {
      webTestClient.get()
        .uri("/detail/AB111111")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$").value(PrisonerDetailDto::class.java) {
          assertThat(it.prisonNumber).isEqualTo("AB111111")
          assertThat(it.offences).containsExactly(
            OffencesDto(
              code = 101,
              date = LocalDate.parse("2001-01-01"),
              establishment = "BELMARSH",
            ),
            OffencesDto(
              code = 48,
              date = LocalDate.parse("2001-01-02"),
              establishment = "DURHAM",
            ),
            OffencesDto(
              code = 99,
              date = LocalDate.parse("2001-01-03"),
              establishment = "FRANKLAND",
            ),
          )
        }
    }

    @Test
    fun `should return offences in custody`() {
      webTestClient.get()
        .uri("/detail/AB111111")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$").value(PrisonerDetailDto::class.java) {
          assertThat(it.prisonNumber).isEqualTo("AB111111")
          assertThat(it.offencesInCustody).containsExactly(
            OffencesInCustodyDto(
              date = LocalDate.parse("1991-01-04"),
              charge = "ASSAULT ON INMATE",
              outcome = "DISMISSED",
              establishment = "FULL SUTTON",
            ),
            OffencesInCustodyDto(
              date = LocalDate.parse("1991-01-03"),
              charge = "FIGHTING",
              outcome = "NOT PROCEEDED WITH",
              establishment = "FRANKLAND",
            ),
            OffencesInCustodyDto(
              date = LocalDate.parse("1991-01-02"),
              charge = "POFFENCE AGAINST GOAD",
              outcome = "NOT PROVEN",
              establishment = "DURHAM",
            ),
            OffencesInCustodyDto(
              date = LocalDate.parse("1991-01-01"),
              charge = "DISOBEYING A LAWFUL ORDER",
              outcome = "PROVED",
              establishment = "BELMARSH",
            ),
          )
        }
    }

    @Test
    fun `should return sentencing`() {
      webTestClient.get()
        .uri("/detail/AB111111")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$").value(PrisonerDetailDto::class.java) {
          assertThat(it.prisonNumber).isEqualTo("AB111111")
          assertThat(it.sentencing).containsExactly(
            SentencingDto(
              lengthDays = 3027,
              changeDate = LocalDate.parse("2004-01-01"),
              SED = LocalDate.parse("2006-01-01"),
              LED = LocalDate.parse("2005-03-03"),
              CRD = LocalDate.parse("2004-04-04"),
              HDCED = LocalDate.parse("2004-03-03"),
            ),
            SentencingDto(
              lengthDays = 2373,
              changeDate = LocalDate.parse("2003-01-01"),
              SED = LocalDate.parse("2006-02-02"),
              LED = LocalDate.parse("2005-02-02"),
              CRD = LocalDate.parse("2004-03-03"),
            ),
            SentencingDto(
              lengthDays = 1278,
              changeDate = LocalDate.parse("2002-01-01"),
              SED = LocalDate.parse("2006-02-01"),
              LED = LocalDate.parse("2004-01-01"),
              CRD = LocalDate.parse("2004-02-02"),
              HDCAD = LocalDate.parse("2002-02-02"),
              HDCED = LocalDate.parse("2004-02-02"),
            ),
            SentencingDto(
              lengthDays = 1278,
              changeDate = LocalDate.parse("2001-01-01"),
              SED = LocalDate.parse("2006-01-01"),
              LED = LocalDate.parse("2005-01-01"),
              CRD = LocalDate.parse("2004-01-01"),
              HDCAD = LocalDate.parse("2001-01-01"),
              HDCED = LocalDate.parse("2004-01-01"),
            ),
          )
        }
    }

    @Test
    fun `should return not found if not found`() {
      webTestClient.get()
        .uri("/detail/AC111111")
        .headers(setAuthorisation(roles = listOf("ROLE_HPA_USER")))
        .exchange()
        .expectStatus()
        .isNotFound
    }
  }
}
