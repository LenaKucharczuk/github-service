package allegro.recruitment.task

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static allegro.recruitment.task.UriPath.REPOSITORIES
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
class GithubRestControllerIT extends Specification {

  @Autowired
  MockMvc mvc

  @Autowired
  ObjectMapper objectMapper

  def "Get repository details successfully"() {
    given: "existing user and existing repository"
    String owner = "userCreatedToTests" // TODO user and repository get from test properties
    String repository = "testRepository"

    when: "ask for repository details"
    def response = mvc.perform(get("${REPOSITORIES}/${owner}/${repository}")).andReturn().response

    then: "repository details are returned"
    response.status == HttpStatus.OK.value()
    with (objectMapper.readValue(response.contentAsString, RepositoryDetails)) {
      it.fullName == "${owner}/${repository}"
    }
  }

  def "Get repository details unsuccessfully"() {
    given: "#owner user and #repository repository"

    when: "ask for repository details"
    def response = mvc.perform(get("${REPOSITORIES}/${owner}/${repository}")).andReturn().response

    then: "404 status is returned"
    response.status == HttpStatus.NOT_FOUND.value()

    where:
    owner                 | repository
    "userCreatedToTests"  | "non-existing"
    "non-exting"          | "testRepository"
  }

  def "Get repository details when Github is down"() {
  }

  def "Get repository details should be effective"() {
    given: "existing user and existing repository"
    String owner = "userCreatedToTests"
    String repository = "testRepository"
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor()
    executor.setCorePoolSize(25)
    executor.setMaxPoolSize(25)
    executor.initialize()
    when: "ask for repository details"
    for (int i = 0; i < 20; ++i) {
      executor.submit({
        println("Start")
        long start = System.nanoTime()
        mvc.perform(get("${REPOSITORIES}/${owner}/${repository}")).andReturn().response
        double elapsedTimeInSecond = (double) (System.nanoTime()-start) / 1_000_000_000
        println("Finished : ${elapsedTimeInSecond}")
      })
    }

    then: "repository details are returned"
    Thread.sleep(5000)
  }
}
