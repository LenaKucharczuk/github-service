package allegro.recruitment.task;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Setter
@Configuration
public class RestConfiguration {

  @Value("${restTemplate.connection.request.timeout:0}")
  private int connectionRequestTimeout;

  @Value("${restTemplate.connect.timeout:0}")
  private int connectTimeout;

  @Value("${restTemplate.read.timeout:0}")
  private int readTimeout;

  @Bean
  public RestTemplate restTemplate() {
    ClientHttpRequestFactory requestFactory = requestFactory();

    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(requestFactory);

    return restTemplate;
  }

  private ClientHttpRequestFactory requestFactory() {
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setConnectionRequestTimeout(connectionRequestTimeout);
    requestFactory.setConnectTimeout(connectTimeout);
    requestFactory.setReadTimeout(readTimeout);

    return requestFactory;
  }
}
