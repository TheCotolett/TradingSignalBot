package it.contrader.model;

import it.contrader.dto.CoinCandleDataID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document("coinCandleData")
public class CoinCandleData {
    @MongoId
    private CoinCandleDataID id;
    private String symbol;
    private LocalDate date;
    private Float price;
}
