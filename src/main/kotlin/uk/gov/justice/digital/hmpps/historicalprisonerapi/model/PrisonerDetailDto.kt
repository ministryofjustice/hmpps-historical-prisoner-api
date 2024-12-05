package uk.gov.justice.digital.hmpps.historicalprisonerapi.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@JsonInclude(NON_NULL)
data class PrisonerDetailModel(
  val prisonNumber: String?,
  val personalDetails: PersonalDetailsDto,
  val addresses: List<AddressesDto>?,
  val aliases: List<AliasesModel>?,
  val category: CategoryDto?,
  val courtHearings: List<CourtHearingsDto>?,
  val hdcInfo: List<HdcInfoDto>?,
  val hdcRecall: List<HdcRecallDto>?,
  val movements: List<MovementsDto>?,
  val offences: List<OffencesDto>?,
  val sentencing: List<SentencingDto>?,
  val adjudications: List<OffencesInCustodyDto>?,
) {
  fun toDto(): PrisonerDetailDto {
    val establishment = if (!movements.isNullOrEmpty()) movements[0].establishment else null
    val courtHearing = if (!courtHearings.isNullOrEmpty()) courtHearings[0] else null
    val effectiveSentence = if (!sentencing.isNullOrEmpty()) sentencing[0] else null
    return PrisonerDetailDto(
      prisonNumber = prisonNumber,
      summary = personalDetails,
      addresses = addresses,
      aliases = aliases?.map { it.toDto() },
      courtHearings = courtHearings,
      hdcInfo = hdcInfo,
      hdcRecall = hdcRecall,
      movements = movements,
      offences = offences,
      offencesInCustody = adjudications,
      sentencing = sentencing,
      sentenceSummary = if (category != null || establishment != null || courtHearing != null || effectiveSentence != null) {
        SentenceSummaryDto(
          category,
          establishment,
          courtHearing,
          effectiveSentence,
        )
      } else {
        null
      },
    )
  }
}

@JsonInclude(NON_NULL)
data class SentenceSummaryDto(
  val category: CategoryDto?,
  val establishment: String?,
  val courtHearing: CourtHearingsDto?,
  val effectiveSentence: SentencingDto?,
)

@JsonInclude(NON_NULL)
data class PrisonerDetailDto(
  val prisonNumber: String?,
  val summary: PersonalDetailsDto,
  val addresses: List<AddressesDto>?,
  val aliases: List<AliasesDto>?,
  val courtHearings: List<CourtHearingsDto>?,
  val hdcInfo: List<HdcInfoDto>?,
  val hdcRecall: List<HdcRecallDto>?,
  val movements: List<MovementsDto>?,
  val offences: List<OffencesDto>?,
  val offencesInCustody: List<OffencesInCustodyDto>?,
  val sentencing: List<SentencingDto>?,
  val sentenceSummary: SentenceSummaryDto?,
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
data class AliasesModel(
  val last: String?,
  val first: String?,
  val middle: String?,
  val birthDate: String?,
) {
  fun toDto(): AliasesDto =
    AliasesDto(last = last, first = first, middle = middle, birthDate = if (!birthDate.isNullOrEmpty()) LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyyMMdd")) else null)
}

@JsonInclude(NON_NULL)
data class AliasesDto(
  val last: String?,
  val first: String?,
  val middle: String?,
  val birthDate: LocalDate?,
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
  val punishments: List<PunishmentDto>? = null,
)

@JsonInclude(NON_NULL)
data class SentencingDto(
  val lengthDays: Int?,
  val changeDate: LocalDate?,
  @get:JsonProperty("SED")
  val SED: LocalDate?,
  @get:JsonProperty("PED")
  val PED: LocalDate? = null,
  @get:JsonProperty("NPD")
  val NPD: LocalDate? = null,
  @get:JsonProperty("LED")
  val LED: LocalDate? = null,
  @get:JsonProperty("CRD")
  val CRD: LocalDate? = null,
  @get:JsonProperty("HDCAD")
  val HDCAD: LocalDate? = null,
  @get:JsonProperty("HDCED")
  val HDCED: LocalDate? = null,
)

@JsonInclude(NON_NULL)
data class PunishmentDto(
  val punishment: String?,
  val duration: Int?,
)
