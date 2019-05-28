package allegro.recruitment.task

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
class ObjectMapperSpec extends Specification {

  @Autowired
  ObjectMapper objectMapper


  def "Deserialize Github response" () {
    given: "part of Github response"
    def jsonFromGithub = '''
      {
        "id": 188696145,
        "name": "testRepository",
        "full_name":"fullName",
        "description":"description",
        "clone_url":"cloneUrl",
        "stargazers_count":123,
        "created_at":"2019-05-26T14:52:20Z",
        "owner": {"login": "userCreatedToTests"}
      }
    '''

    when: "deserialize to RepositoryDetails class"
    def repositoryDetails = objectMapper.readValue(jsonFromGithub, RepositoryDetails.class)

    then: "response is successfully mapped"
    repositoryDetails.fullName == "fullName"
    repositoryDetails.description == "description"
    repositoryDetails.cloneUrl == "cloneUrl"
    repositoryDetails.stars == 123
    repositoryDetails.createdAt.toOffsetDateTime().toString() == "2019-05-26T14:52:20Z"
  }
}
