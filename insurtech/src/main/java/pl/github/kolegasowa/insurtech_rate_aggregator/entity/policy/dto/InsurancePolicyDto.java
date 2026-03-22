package pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class InsurancePolicyDto {

    private Long id;
    private String clientName;
    private BigDecimal amount;
    private String currencyCode;
    private BigDecimal exchangeRate;
    private BigDecimal valueAfterRate;
    private LocalDateTime createdAt;
}
