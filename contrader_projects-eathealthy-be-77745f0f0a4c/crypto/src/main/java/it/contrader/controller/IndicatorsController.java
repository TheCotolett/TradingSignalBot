package it.contrader.controller;

import it.contrader.service.IndicatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("indicator")
public class IndicatorsController {

    @Autowired
    private IndicatorService indicatorService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping("/sma")
    public String getSmaForCoin(String symbol, int value, @RequestParam(required = false)String date){

        //CHECK DATE
        LocalDate sma_date = null;
        if (date.isEmpty()) {sma_date = LocalDate.now().minusDays(1);} else {sma_date= LocalDate.parse(date, formatter);}

        return indicatorService.getSmaForSymbol(symbol, value, sma_date);
    }
    @GetMapping("/ema")
    public String getEmaForCoin(String symbol, int value, @RequestParam(required = false)String date) {

        //CHECK DATE
        LocalDate ema_date = null;
        if (date.isEmpty()) { ema_date = LocalDate.now().minusDays(1);
        } else {ema_date= LocalDate.parse(date, formatter);}

        return indicatorService.getEmaForSymbol(symbol, value, ema_date);
    }

    @GetMapping("/rsi")
    public String getRsiForCoin(String symbol, int value, @RequestParam(required = false)String date) {

        //CHECK DATE
        LocalDate _date = null;
        if (date==null) { _date = LocalDate.now().minusDays(1);
        } else {_date= LocalDate.parse(date, formatter);}

        return indicatorService.getRsiForSymbol(symbol, value, _date);
    }
}
