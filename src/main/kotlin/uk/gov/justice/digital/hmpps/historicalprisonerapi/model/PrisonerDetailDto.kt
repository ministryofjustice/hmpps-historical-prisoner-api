package uk.gov.justice.digital.hmpps.historicalprisonerapi.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import java.time.LocalDate

@JsonInclude(NON_NULL)
data class PrisonerDetailDto(
  val prisonNumber: String?,
  val personalDetails: PersonalDetailsDto,
  val addresses: List<AddressesDto>?,
  val aliases: List<AliasesDto>?,
  val category: CategoryDto?,
  val courtHearings: List<CourtHearingsDto>?,
  val hdcInfo: List<HdcInfoDto>?,
  val hdcRecall: List<HdcRecallDto>?,
  val movements: List<MovementsDto>?,
  val offences: List<OffencesDto>?,
  val offencesInCustody: List<OffencesInCustodyDto>?,
  val sentencing: List<SentencingDto>?,
  val adjudications: List<AdjudicationsDto>?,
)

@JsonInclude(NON_NULL)
data class PersonalDetailsDto(
  val prisonNumber: String?,
  val pncNumber: String?,
  val croNumber: String?,
  val paroleNumbers: String?,
  val initial: String?,
  val firstName: String?,
  val middleName: String?,
  val lastName: String?,
  val sex: String?,
  val dob: LocalDate?,
  val ethnicity: String?,
  val birthCountry: String?,
  val nationality: String?,
  val religion: String?,
  val receptionDate: LocalDate?,
  val maritalStatus: String?,
)

@JsonInclude(NON_NULL)
data class AddressesDto(
  val type: String?,
  val person: String?,
  val street: String?,
  val town: String?,
  val county: String?,
  val sequence: Int?,
)

@JsonInclude(NON_NULL)
data class AliasesDto(
  val last: String?,
  val first: String?,
  val middle: String?,
  val birthDate: String?,
)

@JsonInclude(NON_NULL)
data class CategoryDto(
  val date: LocalDate?,
  val category: String?,
)

@JsonInclude(NON_NULL)
data class CourtHearingsDto(
  val date: LocalDate?,
  val court: String?,
)

@JsonInclude(NON_NULL)
data class HdcInfoDto(
  val stage: String?,
  val status: String?,
  val date: LocalDate?,
  val reasons: String?,
)

@JsonInclude(NON_NULL)
data class HdcRecallDto(
  val createdDate: LocalDate?,
  val curfewEndDate: LocalDate?,
  val outcomeDate: LocalDate?,
  val outcome: String?,
  val reason: String?,
)

@JsonInclude(NON_NULL)
data class MovementsDto(
  val date: LocalDate?,
  val establishment: String?,
  val type: String?,
  val movement: String?,
)

@JsonInclude(NON_NULL)
data class OffencesDto(
  val date: LocalDate?,
  val code: Int?,
  val establishment: String?,
)

@JsonInclude(NON_NULL)
data class OffencesInCustodyDto(
  val date: LocalDate?,
  val outcome: String?,
  val charge: String?,
  val establishment: String?,
)

@JsonInclude(NON_NULL)
data class SentencingDto(
  val lengthDays: Int?,
  val changeDate: LocalDate?,
  val SED: LocalDate?,
  val PED: LocalDate? = null,
  val NPD: LocalDate? = null,
  val LED: LocalDate? = null,
  val CRD: LocalDate? = null,
  val HDCAD: LocalDate? = null,
  val HDCED: LocalDate? = null,
)

@JsonInclude(NON_NULL)
data class AdjudicationsDto(
  val date: LocalDate?,
  val outcome: String?,
  val charge: String?,
  val establishment: String?,
  val punishments: List<PunishmentDto>? = null,
)

@JsonInclude(NON_NULL)
data class PunishmentDto(
  val punishment: String?,
  val duration: Int?,
)
