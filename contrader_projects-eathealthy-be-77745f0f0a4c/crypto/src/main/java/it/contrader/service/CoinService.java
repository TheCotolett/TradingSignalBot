package it.contrader.service;

import it.contrader.dto.CoinCandleDataID;
import it.contrader.dto.CoinGeckoDataDTO;
import it.contrader.mapper.CoinCandleDataConverter;
import it.contrader.model.Coin;
import it.contrader.model.CoinCandleData;
import it.contrader.repository.CoinCandleDataRepository;
import it.contrader.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

@Service
public class CoinService {
    @Autowired
    private CoinCandleDataRepository dataRepository;
    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    RestOperations restTemplate;
    @Autowired
    private CoinCandleDataConverter coinCandleDataConverter;

    public void updateCoin(int days, int n_of_coins){
        List<Coin> coinList = coinRepository.findAll();
        LocalDate today = LocalDate.now();
        LocalDate date;
        int prog_max = days * n_of_coins;
        AtomicInteger prog = new AtomicInteger();

        coinList.stream()
                .limit(n_of_coins)
                .forEach(coin -> {
                    String fixed1 = "https://api.coingecko.com/api/v3/coins/"+coin.getId()+"/history?date=";
                    int i=0;
                    while(i <= days){
                        LocalDate idate= today.minusDays(i);
                        String fixed2 = idate.getDayOfMonth()+"-"+idate.getMonthValue()+"-"+idate.getYear()+"&localization=false";
                        prog.getAndIncrement();
                        if (dataRepository.findById(CoinCandleDataID.builder().date(idate.minusDays(1)).symbol(coin.getSymbol()).build()).isEmpty()){
                            CoinGeckoDataDTO coinGeckoDataDTO = restTemplate.getForObject(
                                    fixed1+fixed2
                                    , CoinGeckoDataDTO.class
                            );
                            System.out.println("FETCHING FROM API: "+coin.getSymbol()+" "+idate);
                            CoinCandleData coinCandleData = CoinCandleData.builder()
                                    .id(
                                            CoinCandleDataID.builder()
                                                    .date(idate.minusDays(1))
                                                    .symbol(coin.getSymbol())
                                                    .build()
                                    )
                                    .price(coinGeckoDataDTO.getMarket_data().getCurrent_price().getUsd())
                                    .date(idate.minusDays(1))
                                    .symbol(coinGeckoDataDTO.getSymbol())
                                    .build();
                            dataRepository.save(coinCandleData);
                            try {
                                sleep(9000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else {System.err.println("SKIPPING: data alredy exists");}
                        i++;
                        System.out.println("PROGRESS: "+prog.toString()+"/"+prog_max);
                    }
                })
        ;

        /*int i=20;
        float tot=0;
        String fixed1 = "https://api.coingecko.com/api/v3/coins/"+symbol+"/history?date=";
        String fixed2 = "-04-2023&localization=false";
        do {
            String url= fixed1+i+fixed2;
            System.out.println(url+" "+i);
            LocalDate testDate = LocalDate.of(2023, 04, i);
            CoinGeckoDataDTO coinGeckoDataDTO = restTemplate.getForObject(
                    url
                    , CoinGeckoDataDTO.class
            );
            CoinCandleData coin = CoinCandleData.builder()
                    .id(
                            CoinCandleDataID.builder()
                                    .date(testDate)
                                    .symbol(symbol)
                                    .build()
                    )
                    .price(coinGeckoDataDTO.getMarket_data().getCurrent_price().getUsd())
                    .date(testDate)
                    .symbol(coinGeckoDataDTO.getSymbol())
                    .build();
            dataRepository.save(coin);
            i++;

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (i<=30);*/

    }

    public void updateCoinsList(){
        Coin[] coinsListDataDTO = restTemplate.getForObject(
                "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=300&page=1&sparkline=false&locale=en",
                Coin[].class
        );

        Arrays.stream(coinsListDataDTO)
                .forEach( coin -> {
                    coinRepository.save(coin);
                });
    }
}
