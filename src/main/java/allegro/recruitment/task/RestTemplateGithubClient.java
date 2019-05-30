package allegro.recruitment.task;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RestTemplateGithubClient implements GithubClient {

  private final RestTemplate restTemplate;

  @Value("${github.api.url}")
  private String githubUrl;

  public RepositoryDetails getRepositoryDetails(String owner, String repositoryName) {
    String url = githubUrl + "/repos/" + owner + "/" + repositoryName;
    try {

      return restTemplate.getForObject(url, RepositoryDetails.class);

    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new ResponseException(e.getMessage(), e.getStatusCode());
    } catch (ResourceAccessException e) {
      throw new ResponseException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
