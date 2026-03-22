package pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.nbp.client.NbpClient;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.dto.InsurancePolicyDto;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.mapper.InsurancePolicyMapper;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.model.InsurancePolicy;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.service.InsurancePolicyService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InsurancePolicyServiceImpl implements InsurancePolicyService {

    private final InsurancePolicyRepository repository;
    private final InsurancePolicyMapper mapper;
    private final NbpClient nbpClient;

    @Override
    @Transactional
    public InsurancePolicyDto calculateAndSave(InsurancePolicyDto policyDto) {

        BigDecimal rate = nbpClient.getExchangeRate(policyDto.getCurrencyCode());
        InsurancePolicy entity = mapper.toEntity(policyDto);
        BigDecimal totalValue = policyDto.getAmount().multiply(rate);
        entity.setExchangeRate(rate);
        entity.setValueAfterRate(totalValue);
        InsurancePolicy savedEntity = repository.save(entity);

        return mapper.toDto(savedEntity);
    }

    @Override
    public List<InsurancePolicyDto> getAllPolicies() {
        return mapper.toDtoList(repository.findAll());
    }
}
