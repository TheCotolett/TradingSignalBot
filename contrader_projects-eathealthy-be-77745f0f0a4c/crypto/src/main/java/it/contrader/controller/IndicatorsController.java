package it.contrader.controller;

import it.contrader.service.IndicatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("indicator")
public class IndicatorsController {

    @Autowired
    private IndicatorService indicatorService;

    @GetMapping("/sma")
    public String getSmaForCoin(String symbol, int value){
        return indicatorService.getSmaForSymbol(symbol, value);
    }
    @GetMapping("/ema")
    public String getEmaForCoin(String symbol, int value){
        return indicatorService.getEmaForSymbol(symbol, value);
    }
}
