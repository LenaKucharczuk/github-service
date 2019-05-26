package allegro.recruitment.task

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static allegro.recruitment.task.UriPath.REPOSITORIES
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@WebMvcTest(controllers = [GithubRestController])
class GithubRestControllerSpec extends Specification {

  @Autowired
  MockMvc mvc

  @Autowired
  GithubClient githubClient

  @Autowired
  ObjectMapper objectMapper

  def "Get repository details successfully"() {
    given: "existing user and existing repository"
    String owner = "userCreatedToTests"
    String repository = "testRepository"
    githubClient.getRepositoryDetails(owner, repository) >> new RepositoryDetails(repository, "", "", 4, null)

    when: "ask for repository details"
    def response = mvc.perform(get("${REPOSITORIES}/${owner}/${repository}")).andReturn().response

    then: "repository details are returned"
    response.status == HttpStatus.OK.value()
    with (objectMapper.readValue(response.contentAsString, RepositoryDetails)) {
      it.fullName == repository
    }
  }

  def "Get repository details unsuccessfully"() {
  }

  @TestConfiguration
  static class StubConfig {
    DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

    @Bean
    GithubClient githubDetailsService() {
      return detachedMockFactory.Stub(GithubClient)
    }
  }
}
