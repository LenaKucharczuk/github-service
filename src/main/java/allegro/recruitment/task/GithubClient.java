package allegro.recruitment.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class GithubClient {

  private final RestTemplate restTemplate;

  private static final String GITHUB_URL = "https://api.github.com";

  RepositoryDetails getRepositoryDetails(String owner, String repositoryName) {
    return restTemplate.getForObject(getRepositoryDetailsUrl(owner, repositoryName), RepositoryDetails.class);
  }

  private String getRepositoryDetailsUrl(String owner, String repositoryName) {
    return GITHUB_URL + "/repos/" + owner + "/" + repositoryName;
  }

}
