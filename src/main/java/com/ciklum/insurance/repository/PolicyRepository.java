package com.ciklum.insurance.repository;
import com.ciklum.insurance.model.Policy;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PolicyRepository extends MongoRepository<Policy, String> {
    List<Policy> findByType(String type);
}