package uk.gov.justice.digital.hmpps.historicalprisonerapi.model

import java.time.LocalDate

interface PrisonerSearchDto {
  fun getPrisonNumber(): String
  fun getReceptionDate(): LocalDate
  fun getLastName(): String
  fun getFirstName(): String
  fun getMiddleName(): String
  fun getDob(): LocalDate
  fun getIsAlias(): Boolean
  fun getAliasLast(): String
  fun getAliasFirst(): String
  fun getAliasMiddle(): String
}
