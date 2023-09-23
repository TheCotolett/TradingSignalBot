package it.contrader.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class IndicatorsDTO {
    private HashMap<String, Double> emas;
    private HashMap<String, Double> smas;
    private double rsi;
}
