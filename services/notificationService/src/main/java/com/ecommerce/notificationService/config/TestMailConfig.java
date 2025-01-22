package com.ecommerce.notificationService.config;

import com.ecommerce.notificationService.model.Notification;
import com.ecommerce.notificationService.repository.NotificationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Configuration
public class TestMailConfig {
    // create a client
    HttpClient client = HttpClient.newHttpClient();
    String APIKEY = "8ba4bb2b-5bef-4491-8b2e-c86f35f5c89f", NAMESPACE = "7fbqb";
    String url = "https://api.testmail.app/api/json?apikey=" + APIKEY + "&namespace=" + NAMESPACE + "&pretty=true";

    @Bean
    public String sendTestMail() throws IOException, InterruptedException, ExecutionException {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return "success";
    }
    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }
}
