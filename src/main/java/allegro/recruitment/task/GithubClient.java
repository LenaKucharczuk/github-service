package allegro.recruitment.task;

import org.springframework.stereotype.Service;

@Service
public interface GithubClient {
  /**
   * Returns repository details
   *
   * @param owner owner of repository
   * @param repositoryName name of public repository
   * @return RepositoryDetails if owner and repository exists
   * @throws ResponseException with http status if owner or repository doesn't exist or in case of server issues
   */
  RepositoryDetails getRepositoryDetails(String owner, String repositoryName);
}
