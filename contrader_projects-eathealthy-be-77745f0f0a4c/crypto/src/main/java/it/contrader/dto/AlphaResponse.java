package it.contrader.dto;

import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlphaResponse {
    @JsonProperty("Meta Data")
    private AlphaMetadata metadata;
    @JsonProperty("Technical Analysis: EMA")
    private IndicatorData data;
}
