package allegro.recruitment.task;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class RepositoryDetails {
  @JsonAlias("full_name")
  private String fullName;

  private String description;

  @JsonAlias("clone_url")
  private String cloneUrl;

  @JsonAlias("stargazers_count")
  private int stars;

  @JsonAlias("created_at")
  private ZonedDateTime createdAt;
}
