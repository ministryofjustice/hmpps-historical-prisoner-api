package uk.gov.justice.digital.hmpps.historicalprisonerapi.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "ADDRESS_LOOKUP", schema = "HPA")
data class AddressLookup(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PK_ADDRESS_LOOKUP", nullable = false)
  val id: Int,

  @Size(max = 8)
  @NotNull
  @Column(name = "PRISON_NUMBER", nullable = false, length = 8)
  val prisonNumber: String,

  @Size(max = 500)
  @Column(name = "ADDRESS_TEXT", length = 500)
  val addressText: String,
)
