package allegro.recruitment.task


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static allegro.recruitment.task.UriPath.REPOSITORIES
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
class GithubRestControllerIT extends Specification {

  @Autowired
  MockMvc mvc

  String existingOwner = "userCreatedToTests"
  String existingRepository = "testRepository"

  def "Get repository details successfully"() {
    given: "existing user and existing repository"

    when: "ask for repository details"
    def response = mvc.perform(get("${REPOSITORIES}/${existingOwner}/${existingRepository}"))

    then: "repository details are returned"
    response
        .andExpect(status().isOk())
        .andExpect(jsonPath('$.fullName').value(existingOwner + "/" + existingRepository))
        .andExpect(jsonPath('$.cloneUrl').value("https://github.com/" + existingOwner + "/" + existingRepository + ".git"))
        .andExpect(jsonPath('$.createdAt').value("2019-05-26T14:52:20Z"))
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
    def response = mvc.perform(get("${REPOSITORIES}/notExistingOwner/${existingRepository}")).andReturn().response

    then: "404 status is returned"
    response.status == HttpStatus.NOT_FOUND.value()
  }
}
