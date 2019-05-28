package allegro.recruitment.task


import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class GithubClientSpec extends Specification {

  RestTemplate restTemplate = Mock {
    getForObject( _, RepositoryDetails.class) >>
        new RepositoryDetails("fullName", "description", "cloneUrl", 0, getSampleDate())
  }

  @Subject
  GithubClient githubClient = new GithubClient(restTemplate)

  def "GetRepositoryDetails"() {
    given:

    when: "ask for repository details"
    def repositoryDetails = githubClient.getRepositoryDetails("owner", "repository")

    then:
    repositoryDetails.fullName == "fullName"
    repositoryDetails.description == "description"
    repositoryDetails.cloneUrl == "cloneUrl"
    repositoryDetails.stars == 0
    repositoryDetails.createdAt == getSampleDate()
  }

  private static ZonedDateTime getSampleDate() {
    ZonedDateTime.of(LocalDateTime.of(2019, 2, 10, 17, 0, 0), ZoneId.of("UTC"))
  }
}
