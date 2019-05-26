package allegro.recruitment.task;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static allegro.recruitment.task.UriPath.REPOSITORIES;

@RestController
@AllArgsConstructor
public class GithubRestController {

  private final GithubDetailsService githubDetailsService;

  @GetMapping(REPOSITORIES + "{owner}/{repositoryName}")
  public ResponseEntity<RepositoryDetails> getRepositoryDetails(@PathVariable String owner, @PathVariable String repositoryName) {
    ResponseEntity<RepositoryDetails> repositoryDetails = githubDetailsService.getRepositoryDetails(owner,
        repositoryName);
    return repositoryDetails;
  }
}
