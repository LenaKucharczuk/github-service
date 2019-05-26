package allegro.recruitment.task;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GithubDetailsService {

  private final GithubClient githubClient;

  ResponseEntity<RepositoryDetails> getRepositoryDetails(String owner, String repositoryName) {
    ResponseEntity<RepositoryDetails> repositoryDetails = githubClient.getRepositoryDetails(owner, repositoryName);
    return repositoryDetails;
  }
}
