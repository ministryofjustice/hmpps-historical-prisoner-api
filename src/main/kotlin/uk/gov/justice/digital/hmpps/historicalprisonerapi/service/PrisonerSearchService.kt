package uk.gov.justice.digital.hmpps.historicalprisonerapi.service

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.Prisoner
import uk.gov.justice.digital.hmpps.historicalprisonerapi.repository.PrisonerRepository
import java.time.LocalDate

@Service
class PrisonerSearchService(private val prisonerRepository: PrisonerRepository) {
  fun findPrisoners(prisonNumber: String?, pnc: String?, cro: String?, pageRequest: Pageable): Page<Prisoner> =
    prisonerRepository.findAll(createIdentifierSpecification(prisonNumber, pnc, cro), pageRequest)

  private fun createIdentifierSpecification(
    prisonNumber: String?,
    pnc: String?,
    cro: String?,
  ): Specification<Prisoner> =
    Specification { root: Root<Prisoner>, _: CriteriaQuery<*>?, builder: CriteriaBuilder ->
      val predicates = mutableListOf<Predicate>()
      prisonNumber?.run { predicates.add(builder.equal(root.get<String>("prisonNumber"), prisonNumber)) }
      pnc?.run { predicates.add(builder.equal(root.get<String>("pncNumber"), pnc)) }
      cro?.run { predicates.add(builder.equal(root.get<String>("croNumber"), cro)) }
      builder.and(*predicates.toTypedArray())
    }

  fun findPrisoners(
    forename: String?,
    surname: String?,
    dateOfBirth: LocalDate?,
    ageFrom: Int?,
    ageTo: Int?,
    gender: Char?,
    hdc: Boolean?,
    lifer: Boolean?,
    pageRequest: Pageable,
  ): Page<Prisoner> =
    prisonerRepository.findAll(
      createDetailsSpecification(forename, surname, dateOfBirth, ageFrom, ageTo, gender, hdc, lifer),
      pageRequest,
    )

  private fun createDetailsSpecification(
    forename: String?,
    surname: String?,
    dateOfBirth: LocalDate?,
    ageFrom: Int?,
    ageTo: Int?,
    gender: Char?,
    hdc: Boolean?,
    lifer: Boolean?,
  ): Specification<Prisoner> =
    Specification { root: Root<Prisoner>, _: CriteriaQuery<*>?, builder: CriteriaBuilder ->
      val predicates = mutableListOf<Predicate>()
      forename?.uppercase()?.run {
        if (this.contains("%")) {
          predicates.add(builder.like(root.get("forename1"), this))
        } else if (this.length == 1) {
          predicates.add(builder.like(root.get("forename1"), "$this%"))
        } else {
          predicates.add(builder.equal(root.get<String>("forename1"), this))
        }
      }
      surname?.uppercase()?.run {
        if (this.contains("%")) {
          predicates.add(builder.like(root.get("surname"), this))
        } else {
          predicates.add(builder.equal(root.get<String>("surname"), this))
        }
      }
      dateOfBirth?.run { predicates.add(builder.equal(root.get<LocalDate>("birthDate"), dateOfBirth)) }
      ageFrom?.run {
        val currentDate = LocalDate.now()
        val birthDateFrom = currentDate.minusYears((ageTo ?: ageFrom).toLong() + 1).plusDays(1)
        val birthDateTo = currentDate.minusYears(ageFrom.toLong())
        predicates.add(builder.between(root.get("birthDate"), birthDateFrom, birthDateTo))
      }
      gender?.uppercase()?.run { predicates.add(builder.equal(root.get<Char>("sex"), this)) }
      hdc?.run { predicates.add(builder.equal(root.get<Boolean>("hasHdc"), hdc)) }
      lifer?.run { predicates.add(builder.equal(root.get<Boolean>("isLifer"), lifer)) }
      builder.and(*predicates.toTypedArray())
    }
}
