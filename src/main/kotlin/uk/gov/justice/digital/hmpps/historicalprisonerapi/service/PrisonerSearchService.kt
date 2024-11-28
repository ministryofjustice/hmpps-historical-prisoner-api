package uk.gov.justice.digital.hmpps.historicalprisonerapi.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.PrisonerSearchDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.repository.PrisonerRepository
import java.time.LocalDate

@Service
class PrisonerSearchService(
  private val prisonerRepository: PrisonerRepository,
) {
  fun findPrisoners(prisonNumber: String?, pnc: String?, cro: String?, pageRequest: Pageable): Page<PrisonerSearchDto> =
    prisonerRepository.findByIdentifiers(
      prisonNumber = prisonNumber?.uppercaseTrimToNull(),
      pnc = pnc?.uppercaseTrimToNull(),
      cro = cro?.uppercaseTrimToNull(),
      pageRequest = pageRequest,
    )

  fun findPrisoners(
    forename: String?,
    surname: String?,
    dateOfBirth: LocalDate?,
    ageFrom: Int?,
    ageTo: Int?,
    gender: String?,
    hdc: Boolean?,
    lifer: Boolean?,
    pageRequest: Pageable,
  ): Page<PrisonerSearchDto> {
    val (forenameSanitised, forenameWithWildcard) = forename?.uppercaseTrimToNull()?.let { fore ->
      if (fore.contains("%")) {
        Pair(null, fore)
      } else if (fore.length == 1) {
        Pair(null, "$fore%")
      } else {
        Pair(fore, null)
      }
    } ?: Pair(null, null)
    val (surnameSanitised, surnameWithWildcard) = surname?.uppercaseTrimToNull()?.let { sur ->
      if (sur.contains("%")) {
        Pair(null, sur)
      } else {
        Pair(sur, null)
      }
    } ?: Pair(null, null)
    val (birthDateFrom, birthDateTo) = ageFrom?.run {
      val currentDate = LocalDate.now()
      val birthDateFrom = currentDate.minusYears((ageTo ?: ageFrom).toLong() + 1).plusDays(1)
      val birthDateTo = currentDate.minusYears(ageFrom.toLong())
      Pair(birthDateFrom, birthDateTo)
    } ?: Pair(null, null)

    return prisonerRepository.findByDetails(
      forename = forenameSanitised,
      forenameWithWildcard = forenameWithWildcard,
      surname = surnameSanitised,
      surnameWithWildcard = surnameWithWildcard,
      birthDateFrom = birthDateFrom ?: dateOfBirth,
      birthDateTo = birthDateTo ?: dateOfBirth,
      ageFrom = ageFrom,
      ageTo = ageTo,
      gender = gender?.uppercaseTrimToNull(),
      hdc = hdc,
      lifer = lifer,
      pageRequest = pageRequest,
    )
  }

  private fun String.uppercaseTrimToNull() = this.uppercase().trim().ifBlank { null }
}
