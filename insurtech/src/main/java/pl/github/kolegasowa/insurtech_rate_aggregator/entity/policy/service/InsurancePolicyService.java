package pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.service;

import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.dto.InsurancePolicyDto;

import java.util.List;

public interface InsurancePolicyService {

    InsurancePolicyDto calculateAndSave(InsurancePolicyDto policyDto);

    List<InsurancePolicyDto> getAllPolicies();
}
