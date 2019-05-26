package allegro.recruitment.task;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class RepositoryDetails {
  @JsonProperty("fullName")
  @JsonAlias("full_name")
  private String fullName;

  @JsonProperty("description")
  private String description;

  @JsonProperty("cloneUrl")
  @JsonAlias("clone_url")
  private String cloneUrl;

  @JsonProperty("stars")
  @JsonAlias("stargazers_count")
  private int stars;

  @JsonProperty("createdAt")
  @JsonAlias("created_at")
  private ZonedDateTime createdAt;
}
