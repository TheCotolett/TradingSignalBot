package it.contrader.controller;

import it.contrader.service.CoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("coin")
public class CoinController {
    @Autowired
    private CoinService coinService;

    @GetMapping("/startUpdateOnCoins")
    public void startUpdateOnCoins(int days, int n_of_coins){
        coinService.updateCoin(days, n_of_coins);
    }
    @GetMapping("/updateCoinList")
    public void startUpdateOnCoinList(){
        coinService.updateCoinsList();
    }
}
