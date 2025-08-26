package com.ciklum.insurance.service;

import com.ciklum.insurance.model.CustomerPolicy;
import java.util.List;

public interface CustomerPolicyService {
    CustomerPolicy createCustomerPolicy(String userId, String policyId);
    List<CustomerPolicy> getAllCustomerPolicies();
    CustomerPolicy getCustomerPolicyById(String id);
    CustomerPolicy update(String id, CustomerPolicy customerPolicy);
    CustomerPolicy deleteCustomerPolicy(String id);
}
