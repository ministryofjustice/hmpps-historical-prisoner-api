package uk.gov.justice.digital.hmpps.historicalprisonerapi.resource

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uk.gov.justice.digital.hmpps.historicalprisonerapi.model.PrisonerDetailDto
import uk.gov.justice.digital.hmpps.historicalprisonerapi.service.PrisonerDetailService
import uk.gov.justice.hmpps.kotlin.common.ErrorResponse

@RestController
@PreAuthorize("hasRole('ROLE_HPA_USER')")
@RequestMapping(value = ["/detail"], produces = ["application/json"])
class PrisonerDetailResource(private val prisonerDetailService: PrisonerDetailService) {

  @GetMapping("/{prisonNumber}")
  @Operation(
    summary = "Retrieve individual prisoner details",
    description = "Requires role ROLE_HPA_USER",
    security = [SecurityRequirement(name = "historical-prisoner-ui-role")],
    responses = [
      ApiResponse(responseCode = "200", description = "prisoner detail", content = [Content(mediaType = "application/json", schema = Schema(implementation = PrisonerDetailDto::class))]),
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
  fun getDetail(@PathVariable prisonNumber: String): String =
    prisonerDetailService.getPrisonerDetail(prisonNumber)
}
