package uk.gov.justice.digital.hmpps.historicalprisonerapi.service

import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.PrisonerSearchDto
import java.time.LocalDate

data class PrisonerSearchImplDto(
  private val prisonNumber: String,
  private val receptionDate: LocalDate? = null,
  private val lastName: String? = null,
  private val firstName: String? = null,
  private val middleName: String? = null,
  private val dob: LocalDate? = null,
  private val isAlias: Boolean = false,
  private val aliasLast: String? = null,
  private val aliasFirst: String? = null,
  private val aliasMiddle: String? = null,
  private val aliasDob: LocalDate? = null,
) : PrisonerSearchDto {
  override fun getPrisonNumber(): String = prisonNumber
  override fun getReceptionDate(): LocalDate? = receptionDate
  override fun getLastName(): String? = lastName
  override fun getFirstName(): String? = firstName
  override fun getMiddleName(): String? = middleName
  override fun getDob(): LocalDate? = dob
  override fun getIsAlias(): Boolean = isAlias
  override fun getAliasLast(): String? = aliasLast
  override fun getAliasFirst(): String? = aliasFirst
  override fun getAliasMiddle(): String? = aliasMiddle
  override fun getAliasDob(): LocalDate? = aliasDob
}
