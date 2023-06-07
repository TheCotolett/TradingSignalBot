package it.contrader.repository;

import it.contrader.dto.CoinCandleDataID;
import it.contrader.model.CoinCandleData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CoinCandleDataRepository extends MongoRepository<CoinCandleData, CoinCandleDataID> {
    List<CoinCandleData> findBySymbolAndDateBetweenOrderByDateAsc(String symbol, LocalDate start_date, LocalDate end_date);
    List<CoinCandleData> findBySymbolOrderByDateAsc(String symbol);
}
