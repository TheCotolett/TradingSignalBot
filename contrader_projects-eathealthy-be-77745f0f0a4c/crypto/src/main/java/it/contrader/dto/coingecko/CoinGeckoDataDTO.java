package it.contrader.dto.coingecko;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CoinGeckoDataDTO {
    private String id;
    private String symbol;
    private LocalDate date;
    private MarketData market_data;

}
