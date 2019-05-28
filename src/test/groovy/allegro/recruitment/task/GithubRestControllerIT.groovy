package allegro.recruitment.task

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static allegro.recruitment.task.UriPath.REPOSITORIES
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@TestPropertySource('classpath:/github-service-IT.properties')
class GithubRestControllerIT extends Specification {

  @Autowired
  MockMvc mvc

  @Autowired
  ObjectMapper objectMapper

  @Value('${existing.owner}')
  String existingOwner

  @Value('${existing.repository}')
  String existingRepository

  def "Get repository details successfully"() {
    given: "existing user and existing repository"

    when: "ask for repository details"
    def response = mvc.perform(get("${REPOSITORIES}/${existingOwner}/${existingRepository}")).andReturn().response

    then: "repository details are returned"
    response.status == HttpStatus.OK.value()
    with (objectMapper.readValue(response.contentAsString, RepositoryDetails)) {
      it.fullName == "${existingOwner}/${existingRepository}"
      it.cloneUrl == "https://github.com/${existingOwner}/${existingRepository}.git"
      it.createdAt.toOffsetDateTime().toString() == "2019-05-26T14:52:20Z"
    }
  }

  def "Get repository details for non-existing repository"() {
    given: "existing user and non-existing repository"

    when: "ask for repository details"
    def response = mvc.perform(get("${REPOSITORIES}/${existingOwner}/notExistingRepo")).andReturn().response

    then: "404 status is returned"
    response.status == HttpStatus.NOT_FOUND.value()
  }

  def "Get repository details for non-existing owner"() {
    given: "non-existing user"

    when: "ask for repository details"
    def response = mvc.perform(get("${REPOSITORIES}/notExistingOwner/someRepository")).andReturn().response

    then: "404 status is returned"
    response.status == HttpStatus.NOT_FOUND.value()
  }
}
