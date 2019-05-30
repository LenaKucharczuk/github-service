package allegro.recruitment.task;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Configuration
public class RestConfiguration {

  private final HttpClientConfigProperties httpProperties;

  @Bean
  public RestTemplate restTemplate(HttpClient httpClient) {
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setHttpClient(httpClient);
    return new RestTemplate(requestFactory);
  }

  @Bean
  public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager, RequestConfig requestConfig) {
    return HttpClientBuilder
        .create()
        .setConnectionManager(poolingHttpClientConnectionManager)
        .setDefaultRequestConfig(requestConfig)
        .build();
  }

  @Bean
  public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
    PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
    result.setMaxTotal(httpProperties.getMaxTotalConnections());
    result.setDefaultMaxPerRoute(httpProperties.getMaxConnectionsPerRoute());
    return result;
  }

  @Bean
  public RequestConfig requestConfig() {
    return RequestConfig.custom()
        .setConnectionRequestTimeout(httpProperties.getConnectionRequestTimeout())
        .setConnectTimeout(httpProperties.getConnectTimeout())
        .setSocketTimeout(httpProperties.getReadTimeout())
        .build();
  }
}

@Configuration
@ConfigurationProperties(prefix = "httpclient")
@Getter
@Setter
class HttpClientConfigProperties {
  private int maxTotalConnections;

  private int maxConnectionsPerRoute;

  private int connectionRequestTimeout;

  private int connectTimeout;

  private int readTimeout;
}
