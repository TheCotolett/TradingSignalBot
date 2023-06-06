package it.contrader.dto;

import it.contrader.model.Coin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoinsListDataDTO {
    private List<Coin> coinsResponse;
}
