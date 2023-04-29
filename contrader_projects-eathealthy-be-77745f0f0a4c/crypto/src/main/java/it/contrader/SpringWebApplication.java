package it.contrader;

import it.contrader.dto.BinanceResponse;
import it.contrader.dto.EmaDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableDiscoveryClient
public class SpringWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringWebApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			BinanceResponse response = restTemplate.getForObject(
					"https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT"
					, BinanceResponse.class);
			EmaDTO emaDTO = restTemplate.getForObject(
					"https://api.taapi.io/ema?secret=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbHVlIjoiNjNlN2U1YWRlYzkzMzI0NGEwMTdmOWZlIiwiaWF0IjoxNjc2MTQyMDI2LCJleHAiOjMzMTgwNjA2MDI2fQ.3mem_QU8iUXWYyiguk2Nt4v6cwLKVheGDHKc-QCJF_4&exchange=binance&symbol=BTC/USDT&interval=1d&"
					,EmaDTO.class
			);

			/*AlphaResponse alphaResponse = restTemplate.getForObject(
					"https://www.alphavantage.co/query?function=EMA&symbol=BTCUSD&interval=daily&time_period=10&series_type=close&apikey=KNCYD8MK37W1OV7P",
					AlphaResponse.class);*/

			System.err.println("\nRESULTS:\n"+response.toString());
			System.err.println("\nRESULTS ALPHA:\n"+emaDTO);
		};
	}
}
