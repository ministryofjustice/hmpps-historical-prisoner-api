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

@Service
class PrisonerSearchService(private val prisonerRepository: PrisonerRepository) {
  fun findPrisoners(prisonNumber: String?, pnc: String?, cro: String?, pageRequest: Pageable): Page<Prisoner> =
    prisonerRepository.findAll(createIdentifierSpecification(prisonNumber, pnc, cro), pageRequest)

  fun createIdentifierSpecification(prisonNumber: String?, pnc: String?, cro: String?): Specification<Prisoner> =
    Specification { root: Root<Prisoner>, _: CriteriaQuery<*>?, builder: CriteriaBuilder ->
      val predicates = mutableListOf<Predicate>()
      prisonNumber?.run { predicates.add(builder.equal(root.get<String>("prisonNumber"), prisonNumber)) }
      pnc?.run { predicates.add(builder.equal(root.get<String>("pncNumber"), pnc)) }
      cro?.run { predicates.add(builder.equal(root.get<String>("croNumber"), cro)) }
      builder.and(*predicates.toTypedArray())
    }
}
