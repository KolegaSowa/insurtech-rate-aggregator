package pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.mapper;

import org.mapstruct.Mapper;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.dto.InsurancePolicyDto;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.model.InsurancePolicy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InsurancePolicyMapper {

    InsurancePolicyDto toDto(InsurancePolicy insurancePolicy);

    InsurancePolicy toEntity(InsurancePolicyDto insurancePolicyDto);

    List<InsurancePolicyDto> toDtoList(List<InsurancePolicy> insurancePolicies);

    List<InsurancePolicy> toEntityList(List<InsurancePolicyDto> insurancePolicyDtos);
}