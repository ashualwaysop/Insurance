package com.ciklum.insurance;

import com.ciklum.insurance.model.Policy;
import com.ciklum.insurance.repository.PolicyRepository;
import com.ciklum.insurance.service.PolicyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PolicyServiceImplTest {

    @Mock
    private PolicyRepository policyRepository;

    @InjectMocks
    private PolicyServiceImpl policyService;

    private Policy policy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        policy = new Policy("1", "Health Plus", "Covers hospital expenses",
                "Health", 1200.0, 500000.0);
    }

    @Test
    void testCreatePolicy() {
        when(policyRepository.save(policy)).thenReturn(policy);

        Policy saved = policyService.createPolicy(policy);

        assertNotNull(saved);
        assertEquals("Health Plus", saved.getName());
    }

    @Test
    void testGetPolicyById() {
        when(policyRepository.findById("1")).thenReturn(Optional.of(policy));

        Policy found = policyService.getPolicyById("1");

        assertNotNull(found);
        assertEquals("Health", found.getType());
    }

    @Test
    void testUpdatePolicy() {
        when(policyRepository.findById("1")).thenReturn(Optional.of(policy));
        when(policyRepository.save(any(Policy.class))).thenReturn(policy);

        Policy updated = policyService.updatePolicy("1", policy);

        assertEquals("Health Plus", updated.getName());
        verify(policyRepository, times(1)).save(policy);
    }

    @Test
    void testDeletePolicy() {
        policyService.deletePolicy("1");
        verify(policyRepository, times(1)).deleteById("1");
    }
}
