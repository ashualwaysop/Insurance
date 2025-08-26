package com.ciklum.insurance.service;
import com.ciklum.insurance.model.Policy;
import com.ciklum.insurance.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class PolicyServiceImpl implements PolicyService {
    @Autowired
    private PolicyRepository policyRepository;
    @Override
    public Policy createPolicy(Policy policy) {
        return policyRepository.save(policy);
    }
    @Override
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }
    @Override
    public List<Policy> getPoliciesByType(String type) {
        return policyRepository.findByType(type);
    }
    @Override
    public Policy getPolicyById(String id) {
        return policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
    }
    @Override
    public Policy updatePolicy(String id, Policy updatedPolicy) {
        Policy existing = getPolicyById(id);
        existing.setName(updatedPolicy.getName());
        existing.setDescription(updatedPolicy.getDescription());
        existing.setType(updatedPolicy.getType());
        existing.setPremium(updatedPolicy.getPremium());
        existing.setCoverageAmount(updatedPolicy.getCoverageAmount());
        return policyRepository.save(existing);
    }
    @Override
    public void deletePolicy(String id) {
        policyRepository.deleteById(id);
    }
}