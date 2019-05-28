package allegro.recruitment.task;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class GithubClient {

  private final RestTemplate restTemplate;

  private static final String GITHUB_URL = "https://api.github.com";

  private static final String AUTH_TOKEN = "cf2315345b5fe420161561c1744b840beb510fae";

  RepositoryDetails getRepositoryDetails(String owner, String repositoryName) {
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, "token " + AUTH_TOKEN);
    return restTemplate.exchange(getRepositoryDetailsUrl(owner, repositoryName), HttpMethod.GET, new HttpEntity(headers), RepositoryDetails.class).getBody();
  }

  private String getRepositoryDetailsUrl(String owner, String repositoryName) {
    return GITHUB_URL + "/repos/" + owner + "/" + repositoryName;
  }

}
