package allegro.recruitment.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static allegro.recruitment.task.UriPath.REPOSITORIES;

@Slf4j
@RestController
@AllArgsConstructor
public class GithubRestController {

  private final GithubClient githubClient;

  @GetMapping(REPOSITORIES + "/{owner}/{repositoryName}")
  public ResponseEntity<RepositoryDetails> getRepositoryDetails(@PathVariable String owner, @PathVariable String repositoryName) {
    long start = System.currentTimeMillis();

    RepositoryDetails repositoryDetails = githubClient.getRepositoryDetails(owner, repositoryName);

    log.info("Fetched repository details for owner: {}, repository: {} in {} ms.", owner, repositoryName, System.currentTimeMillis() - start);
    return ResponseEntity.ok(repositoryDetails);
  }
}
