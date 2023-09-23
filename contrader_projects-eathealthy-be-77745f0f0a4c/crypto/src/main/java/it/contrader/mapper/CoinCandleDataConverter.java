package it.contrader.mapper;

import it.contrader.dto.coingecko.CoinGeckoDataDTO;
import it.contrader.model.CoinCandleData;
import org.springframework.stereotype.Component;

@Component
public class CoinCandleDataConverter {
    public CoinCandleData toCoinCandleDataEntity (CoinGeckoDataDTO dto){
        CoinCandleData coinCandleData = CoinCandleData.builder()
                .price(dto.getMarket_data().getCurrent_price().getUsd())
                .symbol(dto.getSymbol())
                .date(dto.getDate())
                .build();

        return coinCandleData;
    }
}
