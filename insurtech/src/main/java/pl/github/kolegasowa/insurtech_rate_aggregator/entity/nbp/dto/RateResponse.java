package pl.github.kolegasowa.insurtech_rate_aggregator.entity.nbp.dto;

import java.math.BigDecimal;

public record RateResponse(
        String no,
        String effectiveDate,
        BigDecimal mid
) { }
