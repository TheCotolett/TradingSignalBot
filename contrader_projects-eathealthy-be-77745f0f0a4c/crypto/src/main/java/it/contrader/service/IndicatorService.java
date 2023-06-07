package it.contrader.service;

import it.contrader.model.CoinCandleData;
import it.contrader.repository.CoinCandleDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndicatorService {
    @Autowired
    private CoinCandleDataRepository dataRepository;

    public String getSmaForSymbol(String symbol, int value, LocalDate sma_date){
        LocalDate startDate = LocalDate.now().minusDays(value+1);
        List<CoinCandleData> coinCandleData = dataRepository.findBySymbolAndDateBetweenOrderByDateAsc(symbol, startDate, LocalDate.now());
        //System.err.println(coinCandleData.size());
        Float sum=0F;
        for(int i=0; i<coinCandleData.size(); i++){
            sum+=coinCandleData.get(i).getPrice();
        }
        return String.valueOf(sum/value);
    }
    public String getEmaForSymbol(String symbol, int value, LocalDate date){
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

        List<CoinCandleData> coinCandleData = dataRepository.findBySymbolOrderByDateAsc(symbol);
        HashMap<LocalDate, Double> emaValues = this.calculateEma(value, coinCandleData);
        System.out.println(date);
        System.out.println(emaValues.get(date));

        return String.valueOf(emaValues.get(date));
    }

    public HashMap<LocalDate, Double> calculateEma(int value, List<CoinCandleData> coinCandleData){
        double smoothingFactor = 2.0 / (value + 1.0);
        LocalDate startDate = LocalDate.now().minusDays(value+1);
        HashMap<LocalDate, Double> emaValues = new HashMap<>();

        double ema = coinCandleData.get(0).getPrice();
        emaValues.put(coinCandleData.get(0).getDate(), ema);

        for (int i = 1; i < coinCandleData.size(); i++) {
            double v = coinCandleData.get(i).getPrice();
            ema = smoothingFactor * v + (1 - smoothingFactor) * ema;
            emaValues.put(coinCandleData.get(i).getDate(), ema);
        }
        //printHashMap(emaValues);
        return emaValues;
    }
    public static <K, V> void printHashMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }

    public String getRsiForSymbol(String symbol, int value, LocalDate emaDate) {

        int period = 14; // Numero di periodi utilizzati per calcolare l'RSI
        List<CoinCandleData> coinCandleData = dataRepository.findBySymbolAndDateBetweenOrderByDateAsc(symbol, LocalDate.of(2010, 01,01), emaDate.plusDays(1));
        int length = coinCandleData.size();
        coinCandleData.stream().forEach(
                c -> {
                    System.out.println(c.getDate()+" "+c.getPrice());
                }
        );
        System.out.println(length);


        if (length <= period) {
            throw new IllegalArgumentException("Il numero di prezzi deve essere maggiore del periodo di calcolo.");
        }

        List<Double> priceChanges = new ArrayList<>();
        for (int i = 0; i < length - 1; i++) {
            double priceChange =  coinCandleData.get(i + 1).getPrice() - coinCandleData.get(i).getPrice();
            priceChanges.add(priceChange);
        }

        List<Double> gains = new ArrayList<>();
        List<Double> losses = new ArrayList<>();

        for (int i = 0; i < period; i++) {
            double priceChange = priceChanges.get(i);
            if (priceChange >= 0) {
                gains.add(priceChange);
                losses.add(0.0);
            } else {
                gains.add(0.0);
                losses.add(Math.abs(priceChange));
            }
        }

        double averageGain = gains.stream().limit(period).mapToDouble(Double::doubleValue).sum() / period;
        double averageLoss = losses.stream().limit(period).mapToDouble(Double::doubleValue).sum() / period;

        for (int i = period; i < length - 1; i++) {
            double priceChange = priceChanges.get(i);
            if (priceChange >= 0) {
                gains.add(priceChange);
                losses.add(0.0);
            } else {
                gains.add(0.0);
                losses.add(Math.abs(priceChange));
            }

            averageGain = (averageGain * (period - 1) + gains.get(i)) / period;
            averageLoss = (averageLoss * (period - 1) + losses.get(i)) / period;
        }

        double relativeStrength = averageGain / averageLoss;
        double rsi = 100 - (100 / (1 + relativeStrength));

        return String.valueOf(rsi);
    }
}
