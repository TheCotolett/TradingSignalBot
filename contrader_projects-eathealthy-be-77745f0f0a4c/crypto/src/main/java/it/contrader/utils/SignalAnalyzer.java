package it.contrader.utils;

import it.contrader.model.Coin;
import it.contrader.repository.CoinRepository;
import it.contrader.service.CoinService;
import it.contrader.service.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SignalAnalyzer {
    @Autowired
    CoinRepository coinRepository;
    @Autowired
    IndicatorService indicatorService;
    public void checkTodayRsiSignals (){
        LocalDate date = LocalDate.now().minusDays(1);
        List<Coin> coinList = coinRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Coin::getMarket_cap_rank))
                .collect(Collectors.toList());

        for(int i=0; i<5; i++){
            Double rsi = indicatorService.getRsiForSymbol(coinList.get(i).getSymbol(), LocalDate.now());
            if(rsi<=30.0){
                System.out.println("RSI_OVERSOLD "+rsi+" | "+coinList.get(i).getSymbol());
            } else if(rsi>=70){
                System.out.println("RSI_OVERSOLD");
            }
        }
    }
}
