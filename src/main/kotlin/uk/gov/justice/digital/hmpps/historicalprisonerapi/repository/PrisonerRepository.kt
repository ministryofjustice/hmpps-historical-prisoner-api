package uk.gov.justice.digital.hmpps.historicalprisonerapi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.Prisoner

@Repository
interface PrisonerRepository : JpaRepository<Prisoner, Int>, JpaSpecificationExecutor<Prisoner>
