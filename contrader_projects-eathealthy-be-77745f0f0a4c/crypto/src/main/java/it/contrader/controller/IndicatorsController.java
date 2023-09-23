package it.contrader.controller;

import it.contrader.dto.IndicatorsDTO;
import it.contrader.service.IndicatorService;
import it.contrader.utils.MA_VALUES;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("indicator")
public class IndicatorsController {

    @Autowired
    private IndicatorService indicatorService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping("/sma")
    public Double getSmaForCoin(String symbol, int value, @RequestParam(required = false)String date){

        //CHECK DATE
        LocalDate sma_date = null;
        if (date==null) {sma_date = LocalDate.now().minusDays(1);} else {sma_date= LocalDate.parse(date, formatter);}

        return indicatorService.getSmaForSymbol(symbol, value, sma_date);
    }
    @GetMapping("/ema")
    public Double getEmaForCoin(String symbol, int value, @RequestParam(required = false)String date) {

        //CHECK DATE
        LocalDate ema_date = null;
        if (date==null) { ema_date = LocalDate.now().minusDays(1);
        } else {ema_date= LocalDate.parse(date, formatter);}

        return indicatorService.getEmaForSymbol(symbol, value, ema_date);
    }
    @GetMapping("/data_list")
    public IndicatorsDTO getEmasForCoin(String symbol, @RequestParam(required = false)String date) {

        //CHECK DATE
        LocalDate ema_date = null;
        if (date==null) { ema_date = LocalDate.now().minusDays(1);
        } else {ema_date= LocalDate.parse(date, formatter);}

        HashMap<String, Double> emas = new HashMap<>();
        HashMap<String, Double> smas = new HashMap<>();
        for(int i=0; i< MA_VALUES.VALUES.size(); i++){
            emas.put(MA_VALUES.VALUES.get(i).toString(), indicatorService.getEmaForSymbol(symbol, MA_VALUES.VALUES.get(i), ema_date));
            smas.put(MA_VALUES.VALUES.get(i).toString(), indicatorService.getSmaForSymbol(symbol, MA_VALUES.VALUES.get(i), ema_date));
        }
        IndicatorsDTO indicatorsDTO = IndicatorsDTO.builder()
                .emas(emas)
                .smas(smas)
                .rsi( indicatorService.getRsiForSymbol(symbol, ema_date))
                .build();

        return indicatorsDTO;
    }
    @GetMapping("/rsi")
    public Double getRsiForCoin(String symbol, int value, @RequestParam(required = false)String date) {

        //CHECK DATE
        LocalDate _date = null;
        if (date==null) { _date = LocalDate.now().minusDays(1);
        } else {_date= LocalDate.parse(date, formatter);}

        return indicatorService.getRsiForSymbol(symbol, _date);
    }
}
