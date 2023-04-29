package it.contrader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlphaMetadata {
    @JsonProperty("1: Symbol")
    private String symbol;
    @JsonProperty("2: Indicator")
    private String indicator;
    @JsonProperty("4: Interval")
    private String interval;
    @JsonProperty("5: Time Period")
    private String time_period;
}
