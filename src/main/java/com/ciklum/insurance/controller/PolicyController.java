package com.ciklum.insurance.controller;

import com.ciklum.insurance.model.Policy;
import com.ciklum.insurance.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    // CREATE
    @PostMapping
    public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy) {
        return ResponseEntity.ok(policyService.createPolicy(policy));
    }

    // READ - All
    @GetMapping
    public ResponseEntity<List<Policy>> getAllPolicies() {
        return ResponseEntity.ok(policyService.getAllPolicies());
    }

    // READ - By Type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Policy>> getPoliciesByType(@PathVariable String type) {
        return ResponseEntity.ok(policyService.getPoliciesByType(type));
    }

    // READ - By ID
    @GetMapping("/{id}")
    public ResponseEntity<Policy> getPolicyById(@PathVariable String id) {
        Policy policy = policyService.getPolicyById(id);
        return ResponseEntity.ok(policy);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Policy> updatePolicy(@PathVariable String id,
                                               @RequestBody Policy policy) {
        return ResponseEntity.ok(policyService.updatePolicy(id, policy));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePolicy(@PathVariable String id) {
        policyService.deletePolicy(id);
        return ResponseEntity.ok("Policy deleted successfully");
    }
}