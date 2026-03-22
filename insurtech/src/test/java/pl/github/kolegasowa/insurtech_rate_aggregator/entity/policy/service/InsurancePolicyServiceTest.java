package pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.nbp.client.NbpClient;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.dto.InsurancePolicyDto;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.mapper.InsurancePolicyMapper;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.model.InsurancePolicy;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.repository.InsurancePolicyRepository;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.repository.InsurancePolicyServiceImpl;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class InsurancePolicyServiceTest {

    @Mock
    private InsurancePolicyRepository repository;
    @Mock
    private InsurancePolicyMapper mapper;
    @Mock
    private NbpClient nbpClient;

    @InjectMocks
    private InsurancePolicyServiceImpl service;

    @Test
    @DisplayName("Should calculate policy value and save it correctly")
    void shouldCalculateAndSavePolicy() {
        InsurancePolicyDto inputDto = new InsurancePolicyDto();
        inputDto.setAmount(new BigDecimal("100"));
        inputDto.setCurrencyCode("EUR");

        InsurancePolicy entity = new InsurancePolicy();
        InsurancePolicy savedEntity = new InsurancePolicy();
        InsurancePolicyDto expectedDto = new InsurancePolicyDto();
        expectedDto.setValueAfterRate(new BigDecimal("430.00"));

        when(nbpClient.getExchangeRate("EUR")).thenReturn(new BigDecimal("4.30"));
        when(mapper.toEntity(inputDto)).thenReturn(entity);
        when(repository.save(any(InsurancePolicy.class))).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(expectedDto);

        InsurancePolicyDto result = service.calculateAndSave(inputDto);

        assertNotNull(result);
        assertEquals(new BigDecimal("430.00"), result.getValueAfterRate());

        verify(repository, times(1)).save(any(InsurancePolicy.class));
    }
}