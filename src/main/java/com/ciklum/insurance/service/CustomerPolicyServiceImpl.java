package com.ciklum.insurance.service;

import com.ciklum.insurance.model.CustomerPolicy;
import com.ciklum.insurance.model.User;
import com.ciklum.insurance.model.Policy;
import com.ciklum.insurance.repository.CustomerPolicyRepository;
import com.ciklum.insurance.repository.UserRepository;
import com.ciklum.insurance.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerPolicyServiceImpl implements CustomerPolicyService {

    @Autowired
    private CustomerPolicyRepository customerPolicyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Override
    public CustomerPolicy createCustomerPolicy(String userId, String policyId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Policy> policyOpt = policyRepository.findById(policyId);

        if (userOpt.isEmpty() || policyOpt.isEmpty()) {
            throw new RuntimeException("User or Policy not found!");
        }

        User user = userOpt.get();
        Policy policy = policyOpt.get();

        CustomerPolicy cp = new CustomerPolicy();
        cp.setUserId(userId);
        cp.setPolicyId(policyId);
        cp.setPurchaseDate(LocalDate.now());
        cp.setRenewalDate(LocalDate.now().plusYears(1));
        cp.setActive(true);
        cp.setCancelled(false);
        cp.setPremiumPaid(policy.getPremium());
        cp.setUser(user);
        cp.setPolicy(policy);

        return customerPolicyRepository.save(cp);
    }

    @Override
    public List<CustomerPolicy> getAllCustomerPolicies() {
        return customerPolicyRepository.findAll();
    }

    @Override
    public CustomerPolicy getCustomerPolicyById(String id) {
        return customerPolicyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CustomerPolicy not found with id " + id));
    }

    @Override
    public CustomerPolicy update(String id, CustomerPolicy customerPolicy) {
        if (!customerPolicyRepository.existsById(id)) {
            throw new RuntimeException("CustomerPolicy not found with id " + id);
        }
        customerPolicy.setId(id); // ensure updating same doc
        return customerPolicyRepository.save(customerPolicy);
    }
    @Override
    public CustomerPolicy deleteCustomerPolicy(String id) {
        CustomerPolicy policy = customerPolicyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        policy.setActive(false);   // mark inactive
        policy.setCancelled(true); // mark cancelled

        return customerPolicyRepository.save(policy); // return updated object
    }

}
