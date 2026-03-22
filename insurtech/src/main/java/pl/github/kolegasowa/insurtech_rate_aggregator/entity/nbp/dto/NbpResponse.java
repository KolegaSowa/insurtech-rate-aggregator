package pl.github.kolegasowa.insurtech_rate_aggregator.entity.nbp.dto;

import java.util.List;

public record NbpResponse(
        String table,
        String currency,
        String code,
        List<RateResponse> rates
) { }