package allegro.recruitment.task

import com.github.tomakehurst.wiremock.http.Fault
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification
import spock.lang.Unroll

import java.time.ZoneId
import java.time.ZonedDateTime

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@TestPropertySource('classpath:/github-service-IT.properties')
@SpringBootTest(webEnvironment = NONE)
class GithubClientSpec extends Specification {

  @Autowired
  GithubClient githubClient

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(8089)

  def "should perform successful get request"() {
    given:
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
    stubFor(get(urlEqualTo("/repos/owner/repo"))
        .willReturn(aResponse()
        .withStatus(200)
        .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
        .withBody(jsonFromGithub)))

    when:
    def repositoryDetails = githubClient.getRepositoryDetails("owner", "repo")

    then:
    repositoryDetails.fullName == "fullName"
    repositoryDetails.description == "description"
    repositoryDetails.cloneUrl == "cloneUrl"
    repositoryDetails.stars == 123
    repositoryDetails.createdAt == ZonedDateTime.of(2019, 5, 26, 14, 52, 20, 0, ZoneId.of("UTC"))
  }

  @Unroll
  def "should throw exception on #statusCode"() {
    given:
    stubFor(get(urlEqualTo("/repos/owner/repo"))
        .willReturn(aResponse()
        .withStatus(statusCode)))

    when:
    githubClient.getRepositoryDetails("owner", "repo")

    then:
    ResponseException e = thrown()
    e.httpStatus.value() == statusCode

    where:
    statusCode | _
    404        | _
    500        | _
  }

  def "should handle server fault on retrieving resource"() {
    given:
    wireMockRule.stop()

    when:
    githubClient.getRepositoryDetails("owner", "repo")

    then:
    ResponseException e = thrown()
    e.httpStatus == HttpStatus.INTERNAL_SERVER_ERROR
  }

  def "should throw exception on response delay"() {
    given:
    stubFor(get(urlEqualTo("/repos/owner/repo"))
        .willReturn(aResponse()
        .withStatus(200)
        .withBody('{"fullName":"fullName"}')
        .withFixedDelay(300)))

    when:
    githubClient.getRepositoryDetails("owner", "repo")

    then:
    ResponseException e = thrown()
    e.httpStatus == HttpStatus.INTERNAL_SERVER_ERROR
  }

  @Unroll
  def "should throw exception on bad response: #fault"() {
    given:
    stubFor(get(urlEqualTo("/repos/owner/repo"))
        .willReturn(aResponse()
        .withFault(fault)))

    when:
    githubClient.getRepositoryDetails("owner", "repo")

    then:
    ResponseException e = thrown()
    e.httpStatus == HttpStatus.INTERNAL_SERVER_ERROR

    where:
    fault << [Fault.EMPTY_RESPONSE, Fault.MALFORMED_RESPONSE_CHUNK, Fault.RANDOM_DATA_THEN_CLOSE]
  }
}
