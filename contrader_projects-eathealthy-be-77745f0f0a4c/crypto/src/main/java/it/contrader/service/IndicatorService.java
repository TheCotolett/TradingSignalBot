package it.contrader.service;

import it.contrader.model.CoinCandleData;
import it.contrader.repository.CoinCandleDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndicatorService {
    @Autowired
    private CoinCandleDataRepository dataRepository;

    public String getSmaForSymbol(String symbol, int value){
        LocalDate startDate = LocalDate.now().minusDays(value+1);
        List<CoinCandleData> coinCandleData = dataRepository.findBySymbolAndDateBetweenOrderByDateAsc(symbol, startDate, LocalDate.now());
        System.err.println(coinCandleData.size());
        Float sum=0F;
        for(int i=0; i<coinCandleData.size(); i++){
            sum+=coinCandleData.get(i).getPrice();
        }
        return String.valueOf(sum/value);
    }
    public String getEmaForSymbol(String symbol, int value){
        /*float test = 2F/11F;
        Float multiplier = Float.valueOf(2F/Float.valueOf(value+1));
        System.out.println(multiplier);
        LocalDate startDate = LocalDate.now().minusDays(value+1);
        List<CoinCandleData> coinCandleData = dataRepository.findBySymbolAndDateBetween(symbol, startDate, LocalDate.now());
        System.err.println(coinCandleData.size());
        Float sum=0F;
        for(int i=0; i<coinCandleData.size(); i++){
            sum+=coinCandleData.get(i).getPrice();
        }
        Float first_ema = sum/value;
        Float ema = first_ema;
        for(int i=1; i<coinCandleData.size(); i++){
            ema = coinCandleData.get(i).getPrice() * multiplier + ema * (1-multiplier);
        }*/

        double smoothingFactor = 2.0 / (value + 1.0);
        LocalDate startDate = LocalDate.now().minusDays(value+1);
        List<CoinCandleData> coinCandleData = dataRepository.findBySymbolOrderByDateAsc(symbol);

        System.err.println(coinCandleData.size());

        double ema = coinCandleData.get(0).getPrice();
        List<Double> emaValues = new ArrayList<>();
        emaValues.add(ema);
        //System.out.println(coinCandleData.get(0).getPrice() + " "+coinCandleData.get(0).getDate());
        for (int i = 1; i < coinCandleData.size(); i++) {
            double v = coinCandleData.get(i).getPrice();
            ema = smoothingFactor * v + (1 - smoothingFactor) * ema;
            System.out.println(coinCandleData.get(i).getPrice() + " "+coinCandleData.get(i).getDate());
            emaValues.add(ema);
        }

        return String.valueOf(ema);
    }

}
