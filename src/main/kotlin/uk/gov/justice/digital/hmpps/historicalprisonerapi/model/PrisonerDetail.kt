package uk.gov.justice.digital.hmpps.historicalprisonerapi.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "PRISONER_DETAILS", schema = "HPA")
data class PrisonerDetail(
  @Id
  @Size(max = 8)
  @Column(name = "PK_PRISON_NUMBER", nullable = false, length = 8)
  val pkPrisonNumber: String,

  @NotNull
  @Column(name = "PRISONER_DETAILS_ID", nullable = false)
  val prisonerDetailsId: Int,

  @Size(max = 512)
  @Column(name = "PERSONAL_DETAILS", length = 512)
  val personalDetails: String? = null,

  @Lob
  @Column(name = "ADDRESSES")
  val addresses: String? = null,

  @Lob
  @Column(name = "ALIASES")
  val aliases: String? = null,

  @Size(max = 1024)
  @Column(name = "COURT_HEARINGS", length = 1024)
  val courtHearings: String? = null,

  @Lob
  @Column(name = "HDC_INFO")
  val hdcInfo: String? = null,

  @Size(max = 512)
  @Column(name = "HDC_RECALL", length = 512)
  val hdcRecall: String? = null,

  @Lob
  @Column(name = "MOVEMENTS")
  val movements: String? = null,

  @Lob
  @Column(name = "OFFENCES")
  val offences: String? = null,

  @Lob
  @Column(name = "SENTENCING")
  val sentencing: String? = null,

  @Size(max = 256)
  @Column(name = "CATEGORY", length = 256)
  val category: String? = null,

  @Lob
  @Column(name = "ADJUDICATIONS")
  val adjudications: String? = null,
)
