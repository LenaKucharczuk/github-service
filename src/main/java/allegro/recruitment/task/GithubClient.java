package allegro.recruitment.task;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class GithubClient {

  private final RestTemplate restTemplate;

  private static final String GITHUB_URL = "https://api.github.com";

  ResponseEntity<RepositoryDetails> getRepositoryDetails(String owner, String repositoryName) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "token cf2315345b5fe420161561c1744b840beb510fae"); // TODO Token get from properties
    HttpEntity httpEntity = new HttpEntity(headers);

    long start = System.nanoTime();
    ResponseEntity<RepositoryDetails> responseEntity =
        restTemplate.exchange(getUrl(owner, repositoryName), HttpMethod.GET, httpEntity, RepositoryDetails.class);
    long elapsed = System.nanoTime()-start;
    double elapsedTimeInSecond = (double) elapsed / 1_000_000_000;
    System.out.println(elapsedTimeInSecond);
    return  responseEntity;
  }

  private String getUrl(String owner, String repositoryName) {
    return String.format("%s/repos/%s/%s", GITHUB_URL, owner, repositoryName);
  }

}
