package uk.gov.justice.digital.hmpps.historicalprisonerapi.resource

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.ValidationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.Prisoner
import uk.gov.justice.digital.hmpps.historicalprisonerapi.service.PrisonerSearchService
import uk.gov.justice.hmpps.kotlin.common.ErrorResponse

@RestController
@PreAuthorize("hasRole('ROLE_HPA_USER')")
@RequestMapping(value = ["/identifiers"], produces = ["application/json"])
class IdentifierSearchResource(private val prisonerSearchService: PrisonerSearchService) {

  @GetMapping
  @Operation(
    summary = "Retrieve prisoners from identifier search terms",
    description = "Requires role ROLE_HPA_USER",
    security = [SecurityRequirement(name = "historical-prisoner-ui-role")],
    responses = [
      ApiResponse(responseCode = "200", description = "list of prisoner numbers"),
      ApiResponse(
        responseCode = "401",
        description = "Unauthorized to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "403",
        description = "Forbidden to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
    ],
  )
  fun findPrisonersWithIdentifiers(prisonNumber: String?, pnc: String?, cro: String?, pageRequest: Pageable): Page<Prisoner> {
    if (prisonNumber.isNullOrBlank() && pnc.isNullOrBlank() && cro.isNullOrBlank()) {
      throw ValidationException("At least one identifier must be provided")
    }
    return prisonerSearchService.findPrisoners(prisonNumber = prisonNumber, pnc = pnc, cro = cro, pageRequest = pageRequest)
  }
}
