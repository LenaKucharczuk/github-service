package allegro.recruitment.task;

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
  @JsonProperty("full_name")
  private String fullName;

  @JsonProperty("description")
  private String description;

  @JsonProperty("clone_url")
  private String cloneUrl;

  @JsonProperty("stargazers_count")
  private int stars;

  @JsonProperty("created_at")
  private ZonedDateTime createdAt;
}
