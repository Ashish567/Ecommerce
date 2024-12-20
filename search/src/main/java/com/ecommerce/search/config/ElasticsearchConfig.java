package com.ecommerce.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    private static final String CLUSTER_URL = "";
    private static final int PORT = 443;
    private static final String SCHEME = "https";
    private static final String API_KEY = "";

    /**
     * Configures the low-level REST client for Elasticsearch.
     *
     * @return RestClient
     */
    @Bean
    public RestClient restClient() {
        return RestClient.builder(new HttpHost(CLUSTER_URL, PORT, SCHEME))
                .setDefaultHeaders(new org.apache.http.Header[]{
                        new BasicHeader(HttpHeaders.AUTHORIZATION, "ApiKey " + API_KEY)
                })
                .setRequestConfigCallback(requestConfigBuilder ->
                        requestConfigBuilder
                                .setConnectTimeout(5000)
                                .setSocketTimeout(60000)
                                .setConnectionRequestTimeout(0)
                ).build();
    }

    /**
     * Configures the Elasticsearch client using the RestClientTransport.
     *
     * @param restClient Low-level REST client
     * @return ElasticsearchClient
     */
    @Bean
    public ElasticsearchClient elasticsearchClient(RestClient restClient) {
        RestClientTransport transport = new RestClientTransport(
                restClient,
                new JacksonJsonpMapper()
        );
        return new ElasticsearchClient(transport);
    }
}
