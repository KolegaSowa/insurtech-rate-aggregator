package pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.model.InsurancePolicy;

public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Long> {
}
