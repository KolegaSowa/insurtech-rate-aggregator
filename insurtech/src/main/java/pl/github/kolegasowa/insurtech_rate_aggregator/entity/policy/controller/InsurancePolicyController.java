package pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.dto.InsurancePolicyDto;
import pl.github.kolegasowa.insurtech_rate_aggregator.entity.policy.service.InsurancePolicyService;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
public class InsurancePolicyController {

    private final InsurancePolicyService insurancePolicyService;

    @PostMapping
    public ResponseEntity<InsurancePolicyDto> createPolicy(@RequestBody InsurancePolicyDto dto) {
        InsurancePolicyDto savedPolicy = insurancePolicyService.calculateAndSave(dto);
        return new ResponseEntity<>(savedPolicy, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InsurancePolicyDto>> getAllPolicies() {
        return ResponseEntity.ok(insurancePolicyService.getAllPolicies());
    }
}
