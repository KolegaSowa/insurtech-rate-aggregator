package pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.github.kolegasowa.insurtech_rate_aggregator.config.SecurityConfig;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.dto.InsurancePolicyDto;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.service.InsurancePolicyService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InsurancePolicyController.class)
@Import(SecurityConfig.class)
class InsurancePolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InsurancePolicyService insurancePolicyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return 201 Created and saved policy when POST is valid")
    void shouldCreatePolicy() throws Exception {
        InsurancePolicyDto inputDto = new InsurancePolicyDto();
        inputDto.setClientName("Jan Kowalski");
        inputDto.setAmount(new BigDecimal("100"));
        inputDto.setCurrencyCode("EUR");

        InsurancePolicyDto savedDto = new InsurancePolicyDto();
        savedDto.setId(1L);
        savedDto.setClientName("Jan Kowalski");
        savedDto.setValueAfterRate(new BigDecimal("427.68"));

        when(insurancePolicyService.calculateAndSave(any(InsurancePolicyDto.class)))
                .thenReturn(savedDto);

        mockMvc.perform(post("/api/policies")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.valueAfterRate").value(427.68));
    }

}