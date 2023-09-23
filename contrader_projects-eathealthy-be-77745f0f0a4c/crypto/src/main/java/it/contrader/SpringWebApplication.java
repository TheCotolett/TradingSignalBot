package it.contrader;

import it.contrader.utils.SignalAnalyzer;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoRepositories
@EnableSwagger2
public class SpringWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringWebApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.any()).build();
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		ClientHttpRequestFactory requestFactory = new
				HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		return restTemplate;
	}

	@Bean
	public CommandLineRunner run(SignalAnalyzer analyzer) throws Exception {
		return args -> {
			analyzer.checkTodayRsiSignals();
		};
	}
}
