package allegro.recruitment.task;

import lombok.Setter;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Setter
@Configuration
public class RestConfiguration {

  @Value("${httpClient.connectionPool.max.connections.total:25}")
  private int maxTotalConnections;

  @Value("${httpClient.connectionPool.max.connections.perRoute:25}")
  private int maxConnectionsPerRoute;

  @Value("${httpClient.connection.request.timeout:0}")
  private int connectionRequestTimeout;

  @Value("${httpClient.connect.timeout:0}")
  private int connectTimeout;

  @Value("${httpClient.read.timeout:0}")
  private int readTimeout;

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
    result.setMaxTotal(maxTotalConnections);
    result.setDefaultMaxPerRoute(maxConnectionsPerRoute);
    return result;
  }

  @Bean
  public RequestConfig requestConfig() {
    return RequestConfig.custom()
        .setConnectionRequestTimeout(connectionRequestTimeout)
        .setConnectTimeout(connectTimeout)
        .setSocketTimeout(readTimeout)
        .build();
  }
}
