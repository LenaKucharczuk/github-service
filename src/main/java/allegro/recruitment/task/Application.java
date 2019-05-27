package allegro.recruitment.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:/github-service.properties", ignoreResourceNotFound = true)
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
