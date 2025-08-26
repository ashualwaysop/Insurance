package com.ciklum.insurance.repository;

import com.ciklum.insurance.model.CustomerPolicy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerPolicyRepository extends MongoRepository<CustomerPolicy, String> {
    List<CustomerPolicy> findByUserId(String userId);
}
