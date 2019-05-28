package allegro.recruitment.task

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import static allegro.recruitment.task.UriPath.REPOSITORIES
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [GithubRestController])
class GithubRestControllerSpec extends Specification {

  @Autowired
  MockMvc mvc

  @Autowired
  GithubClient githubClient

  @Autowired
  ObjectMapper objectMapper

  def "Get repository details"() {
    given: "existing user and existing repository"
    def creationDate = getSampleDate()
    githubClient.getRepositoryDetails(_, _) >> new RepositoryDetails("fullName", "description", "cloneUrl", 0, creationDate)

    when: "ask for repository details"
    def response = mvc.perform(get("${REPOSITORIES}/owner/repository"))

    then: "repository details are returned"
    response
        .andExpect(status().isOk())
        .andExpect(jsonPath('$.fullName').value("fullName"))
        .andExpect(jsonPath('$.description').value("description"))
        .andExpect(jsonPath('$.cloneUrl').value("cloneUrl"))
        .andExpect(jsonPath('$.stars').value(0))
        .andExpect(jsonPath('$.createdAt').value(creationDate.toOffsetDateTime().format(DateTimeFormatter.ISO_DATE_TIME)))
  }

  private static ZonedDateTime getSampleDate() {
    ZonedDateTime.of(LocalDateTime.of(2019, 2, 10, 17, 0, 0), ZoneId.of("UTC"))
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
