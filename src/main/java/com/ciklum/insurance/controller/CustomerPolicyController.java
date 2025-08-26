package com.ciklum.insurance.controller;

import com.ciklum.insurance.model.CustomerPolicy;
import com.ciklum.insurance.service.CustomerPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer-policies")
public class CustomerPolicyController {

    @Autowired
    private CustomerPolicyService customerPolicyService;

    // CREATE
    @PostMapping
    public ResponseEntity<CustomerPolicy> create(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        String policyId = body.get("policyId");

        if (userId == null || policyId == null) {
            return ResponseEntity.badRequest().build();
        }

        CustomerPolicy saved = customerPolicyService.createCustomerPolicy(userId, policyId);
        return ResponseEntity.ok(saved);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<CustomerPolicy>> getAll() {
        return ResponseEntity.ok(customerPolicyService.getAllCustomerPolicies());
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<CustomerPolicy> getById(@PathVariable String id) {
        return ResponseEntity.ok(customerPolicyService.getCustomerPolicyById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerPolicy> update(@PathVariable String id,
                                                 @RequestBody CustomerPolicy body) {
        return ResponseEntity.ok(customerPolicyService.update(id, body));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        customerPolicyService.deleteCustomerPolicy(id);
        return ResponseEntity.noContent().build();
    }
}
