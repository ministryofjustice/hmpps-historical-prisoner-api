package uk.gov.justice.digital.hmpps.historicalprisonerapi.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.Prisoner
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.PrisonerSearchDto
import java.time.LocalDate

@Repository
interface PrisonerRepository :
  JpaRepository<Prisoner, Int>,
  JpaSpecificationExecutor<Prisoner> {

  @Query(
    """
    $SELECT_CLAUSE
      FROM HPA.PRISONERS
     WHERE (:prisonNumber is null or PRISON_NUMBER = :prisonNumber)
       AND (:pnc is null or PNC_NUMBER = :pnc)
       AND (:cro is null or CRO_NUMBER = :cro)
       AND (:gender is null or SEX = :gender)
       AND (:hdc is null or HAS_HDC = :hdc)
       AND (:lifer is null or IS_LIFER = :lifer)
    ) NUMBERED_ROWS
    $WHERE_ORDER_CLAUSE
    """,
    countQuery = """
    $SELECT_COUNT_CLUSE
      FROM HPA.PRISONERS
     WHERE (:prisonNumber is null or PRISON_NUMBER = :prisonNumber)
       AND (:pnc is null or PNC_NUMBER = :pnc)
       AND (:cro is null or CRO_NUMBER = :cro)
       AND (:gender is null or SEX = :gender)
       AND (:hdc is null or HAS_HDC = :hdc)
       AND (:lifer is null or IS_LIFER = :lifer)
    ) NUMBERED_ROWS
    WHERE ROW_NUM = 1
    """,
    nativeQuery = true,
  )
  fun findByIdentifiers(
    prisonNumber: String?,
    pnc: String?,
    cro: String?,
    gender: String?,
    hdc: Boolean?,
    lifer: Boolean?,
    pageRequest: Pageable,
  ): Page<PrisonerSearchDto>

  @Query(
    """
    $SELECT_CLAUSE
      FROM HPA.PRISONERS
     WHERE (:forename is null or FORENAME_1 = :forename)
       AND (:forenameWithWildcard is null or FORENAME_1 like :forenameWithWildcard)
       AND (:surname is null or SURNAME = :surname)
       AND (:surnameWithWildcard is null or SURNAME like :surnameWithWildcard)
       AND (:birthDateFrom is null or BIRTH_DATE >= :birthDateFrom)
       AND (:birthDateTo is null or BIRTH_DATE <= :birthDateTo)
       AND (:gender is null or SEX = :gender)
       AND (:hdc is null or HAS_HDC = :hdc)
       AND (:lifer is null or IS_LIFER = :lifer)
    ) NUMBERED_ROWS
    $WHERE_ORDER_CLAUSE""",
    countQuery = """
    $SELECT_COUNT_CLUSE
      FROM HPA.PRISONERS
     WHERE (:forename is null or FORENAME_1 = :forename)
       AND (:forenameWithWildcard is null or FORENAME_1 like :forenameWithWildcard)
       AND (:surname is null or SURNAME = :surname)
       AND (:surnameWithWildcard is null or SURNAME like :surnameWithWildcard)
       AND (:birthDateFrom is null or BIRTH_DATE >= :birthDateFrom)
       AND (:birthDateTo is null or BIRTH_DATE <= :birthDateTo)
       AND (:gender is null or SEX = :gender)
       AND (:hdc is null or HAS_HDC = :hdc)
       AND (:lifer is null or IS_LIFER = :lifer)
    ) NUMBERED_ROWS
    WHERE ROW_NUM = 1
    """,
    nativeQuery = true,
  )
  fun findByDetails(
    forename: String?,
    forenameWithWildcard: String?,
    surname: String?,
    surnameWithWildcard: String?,
    birthDateFrom: LocalDate?,
    birthDateTo: LocalDate?,
    ageFrom: Int?,
    ageTo: Int?,
    gender: String?,
    hdc: Boolean?,
    lifer: Boolean?,
    pageRequest: Pageable,
  ): Page<PrisonerSearchDto>

  @Query(
    """
    $SELECT_CLAUSE
      FROM HPA.PRISONERS
     WHERE PRISON_NUMBER IN (
       SELECT DISTINCT PRISON_NUMBER FROM HPA.ADDRESS_LOOKUP WHERE CONTAINS(ADDRESS_TEXT, :addressTerms)
     )
       AND (:gender is null or SEX = :gender)
       AND (:hdc is null or HAS_HDC = :hdc)
       AND (:lifer is null or IS_LIFER = :lifer)
    ) NUMBERED_ROWS
  $WHERE_ORDER_CLAUSE""",
    countQuery = """
    $SELECT_COUNT_CLUSE
      FROM HPA.PRISONERS
     WHERE PRISON_NUMBER IN (
       SELECT DISTINCT PRISON_NUMBER FROM HPA.ADDRESS_LOOKUP WHERE CONTAINS(ADDRESS_TEXT, :addressTerms)
     )
       AND (:gender is null or SEX = :gender)
       AND (:hdc is null or HAS_HDC = :hdc)
       AND (:lifer is null or IS_LIFER = :lifer)
    ) NUMBERED_ROWS
    WHERE ROW_NUM = 1
    """,
    nativeQuery = true,
  )
  fun findByAddresses(
    addressTerms: String,
    gender: String?,
    hdc: Boolean?,
    lifer: Boolean?,
    pageRequest: Pageable,
  ): Page<PrisonerSearchDto>

  companion object {
    const val SELECT_CLAUSE = """
      SELECT  PRISON_NUMBER      AS prisonNumber,
              RECEPTION_DATE     AS receptionDate,
              PRIMARY_SURNAME    AS lastName,
              PRIMARY_FORENAME_1 AS firstName,
              PRIMARY_FORENAME_2 AS middleName,
              PRIMARY_BIRTH_DATE AS dob,
              IS_ALIAS           AS isAlias,
              SURNAME            AS aliasLast,
              FORENAME_1         AS aliasFirst,
              FORENAME_2         AS aliasMiddle,
              BIRTH_DATE         AS aliasDob
        FROM (
        SELECT
          row_number()
          OVER ( PARTITION BY PRISON_NUMBER
          ORDER BY (IS_ALIAS) ) ROW_NUM,
      *"""
    const val SELECT_COUNT_CLUSE = """
      SELECT COUNT(*) As totalRows
        FROM (
               SELECT
                 row_number()
                 OVER ( PARTITION BY PRISON_NUMBER
                   ORDER BY (IS_ALIAS) ) ROW_NUM,
                 *
    """
    const val WHERE_ORDER_CLAUSE = """
      WHERE ROW_NUM = 1
      ORDER BY IS_ALIAS, PRIMARY_SURNAME, PRIMARY_INITIAL, BIRTH_DATE, RECEPTION_DATE DESC
    """
  }
}
