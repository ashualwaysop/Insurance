package com.ciklum.insurance.service;
import com.ciklum.insurance.model.Policy;
import java.util.List;
public interface PolicyService {
    Policy createPolicy(Policy policy);
    List<Policy> getAllPolicies();
    List<Policy> getPoliciesByType(String type);
    Policy getPolicyById(String id);
    Policy updatePolicy(String id, Policy policy);
    void deletePolicy(String id);
}