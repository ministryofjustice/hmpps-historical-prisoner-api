package uk.gov.justice.digital.hmpps.historicalprisonerapi.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class AddressLookupRepositoryTest {

  @Autowired
  lateinit var addressLookupRepository: AddressLookupRepository

  @Test
  fun `should find prisoner detail`() {
    val foundPrisonerDetail = addressLookupRepository.findPrisonersWithAddresses("John, Smith")
    assertThat(foundPrisonerDetail).containsExactlyInAnyOrder("AB111111", "AB111112", "BF123451", "BF123452", "BF123453", "BF123454")
  }
}
