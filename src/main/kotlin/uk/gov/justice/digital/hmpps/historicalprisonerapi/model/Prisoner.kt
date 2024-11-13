package uk.gov.justice.digital.hmpps.historicalprisonerapi.model

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
open class Prisoner {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PK_PRISONER", nullable = false)
  open var id: Int? = null

  @Size(max = 8)
  @NotNull
  @Column(name = "PRISON_NUMBER", nullable = false, length = 8)
  open var prisonNumber: String? = null

  @NotNull
  @Column(name = "PERSON_IDENTIFIER", nullable = false, precision = 10)
  open var personIdentifier: BigDecimal? = null

  @Size(max = 24)
  @Column(name = "SURNAME", length = 24)
  open var surname: String? = null

  @Size(max = 13)
  @Column(name = "FORENAME_1", length = 13)
  open var forename1: String? = null

  @Size(max = 13)
  @Column(name = "FORENAME_2", length = 13)
  open var forename2: String? = null

  @Column(name = "BIRTH_DATE")
  open var birthDate: LocalDate? = null

  @NotNull
  @Column(name = "IS_ALIAS", nullable = false)
  open var isAlias: Boolean? = false

  @Column(name = "SEX")
  open var sex: Char? = null

  @Size(max = 14)
  @Column(name = "PNC_NUMBER", length = 14)
  open var pncNumber: String? = null

  @Size(max = 14)
  @Column(name = "CRO_NUMBER", length = 14)
  open var croNumber: String? = null

  @NotNull
  @Column(name = "HAS_HDC", nullable = false)
  open var hasHdc: Boolean? = false

  @NotNull
  @Column(name = "IS_LIFER", nullable = false)
  open var isLifer: Boolean? = false

  @Column(name = "RECEPTION_DATE")
  open var receptionDate: LocalDate? = null

  @Size(max = 24)
  @Column(name = "PRIMARY_SURNAME", length = 24)
  open var primarySurname: String? = null

  @Size(max = 13)
  @Column(name = "PRIMARY_FORENAME_1", length = 13)
  open var primaryForename1: String? = null

  @Column(name = "PRIMARY_INITIAL")
  open var primaryInitial: Char? = null

  @Size(max = 13)
  @Column(name = "PRIMARY_FORENAME_2", length = 13)
  open var primaryForename2: String? = null

  @Column(name = "PRIMARY_BIRTH_DATE")
  open var primaryBirthDate: LocalDate? = null
}
