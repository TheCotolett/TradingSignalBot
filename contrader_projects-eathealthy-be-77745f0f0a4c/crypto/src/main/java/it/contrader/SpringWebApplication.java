package it.contrader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
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
		return builder.build();
	}

	/*@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			int i=1;
			float tot=0;
			String fixed1 = "https://api.coingecko.com/api/v3/coins/vethor-token/history?date=";
			String fixed2 = "-04-2023&localization=false";
			List<Float> list = new ArrayList<>();
			do {
				String url= fixed1+i+fixed2;
				System.out.println(url+" "+i);
				CoinGeckoDataDTO coinGeckoDataDTO = restTemplate.getForObject(
						url
							, CoinGeckoDataDTO.class
				);
				i++;
				tot = tot +coinGeckoDataDTO.getMarket_data().getCurrent_price().getUsd();
				list.add(coinGeckoDataDTO.getMarket_data().getCurrent_price().getUsd());

				sleep(1000);
			} while (i<=30);


			System.err.println("\n\nCOINGECKO 30 SMA:\n" + tot/30);

		};
	}*/
}
