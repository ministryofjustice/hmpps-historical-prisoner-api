package uk.gov.justice.digital.hmpps.historicalprisonerapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "PRISONERS", schema = "HPA")
data class Prisoner(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PK_PRISONER", nullable = false)
  @JsonIgnore
  val id: Int,

  @Size(max = 8)
  @NotNull
  @Column(name = "PRISON_NUMBER", nullable = false, length = 8)
  val prisonNumber: String,

  @NotNull
  @Column(name = "PERSON_IDENTIFIER", nullable = false, precision = 10)
  @JsonIgnore
  val personIdentifier: BigDecimal,

  @Size(max = 24)
  @Column(name = "SURNAME", length = 24)
  val surname: String? = null,

  @Size(max = 13)
  @Column(name = "FORENAME_1", length = 13)
  val forename1: String? = null,

  @Size(max = 13)
  @Column(name = "FORENAME_2", length = 13)
  val forename2: String? = null,

  @Column(name = "BIRTH_DATE")
  @JsonIgnore
  val birthDate: LocalDate? = null,

  @NotNull
  @Column(name = "IS_ALIAS", nullable = false)
  val isAlias: Boolean,

  @Column(name = "SEX")
  @JsonIgnore
  val sex: Char? = null,

  @Size(max = 14)
  @Column(name = "PNC_NUMBER", length = 14)
  @JsonIgnore
  val pncNumber: String? = null,

  @Size(max = 14)
  @Column(name = "CRO_NUMBER", length = 14)
  @JsonIgnore
  val croNumber: String? = null,

  @NotNull
  @Column(name = "HAS_HDC", nullable = false)
  @JsonIgnore
  val hasHdc: Boolean,

  @NotNull
  @Column(name = "IS_LIFER", nullable = false)
  @JsonIgnore
  val isLifer: Boolean,

  @Column(name = "RECEPTION_DATE")
  val receptionDate: LocalDate? = null,

  @Size(max = 24)
  @Column(name = "PRIMARY_SURNAME", length = 24)
  val primarySurname: String? = null,

  @Size(max = 13)
  @Column(name = "PRIMARY_FORENAME_1", length = 13)
  val primaryForename1: String? = null,

  @Size(max = 13)
  @Column(name = "PRIMARY_FORENAME_2", length = 13)
  val primaryForename2: String? = null,

  @Column(name = "PRIMARY_BIRTH_DATE")
  val primaryBirthDate: LocalDate? = null,
)
