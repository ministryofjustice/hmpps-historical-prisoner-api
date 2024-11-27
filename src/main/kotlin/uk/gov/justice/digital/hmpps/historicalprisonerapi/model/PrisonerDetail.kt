package uk.gov.justice.digital.hmpps.historicalprisonerapi.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.Nationalized

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
  @Nationalized
  @Column(name = "PERSONAL_DETAILS", length = 512)
  val personalDetails: String? = null,

  @Nationalized
  @Lob
  @Column(name = "ADDRESSES")
  val addresses: String? = null,

  @Nationalized
  @Lob
  @Column(name = "ALIASES")
  val aliases: String? = null,

  @Size(max = 1024)
  @Nationalized
  @Column(name = "COURT_HEARINGS", length = 1024)
  val courtHearings: String? = null,

  @Nationalized
  @Lob
  @Column(name = "HDC_INFO")
  val hdcInfo: String? = null,

  @Size(max = 512)
  @Nationalized
  @Column(name = "HDC_RECALL", length = 512)
  val hdcRecall: String? = null,

  @Nationalized
  @Lob
  @Column(name = "MOVEMENTS")
  val movements: String? = null,

  @Nationalized
  @Lob
  @Column(name = "OFFENCES")
  val offences: String? = null,

  @Nationalized
  @Lob
  @Column(name = "OFFENCES_IN_CUSTODY")
  val offencesInCustody: String? = null,

  @Nationalized
  @Lob
  @Column(name = "SENTENCING")
  val sentencing: String? = null,

  @Size(max = 256)
  @Nationalized
  @Column(name = "CATEGORY", length = 256)
  val category: String? = null,

  @Nationalized
  @Lob
  @Column(name = "ADJUDICATIONS")
  val adjudications: String? = null,
)
