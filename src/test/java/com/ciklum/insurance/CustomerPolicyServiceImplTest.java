package com.ciklum.insurance;

import com.ciklum.insurance.model.CustomerPolicy;
import com.ciklum.insurance.model.Policy;
import com.ciklum.insurance.model.User;
import com.ciklum.insurance.repository.CustomerPolicyRepository;
import com.ciklum.insurance.repository.PolicyRepository;
import com.ciklum.insurance.repository.UserRepository;
import com.ciklum.insurance.service.CustomerPolicyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerPolicyServiceImplTest {

    @Mock
    private CustomerPolicyRepository customerPolicyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PolicyRepository policyRepository;

    @InjectMocks
    private CustomerPolicyServiceImpl service;

    private User user;
    private Policy policy;
    private CustomerPolicy customerPolicy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User("U1", "John Doe", "john@example.com", "9876543210", "Delhi");
        policy = new Policy("P1", "Health Insurance", "Covers expenses", "Health", 500000.0, 2);

        customerPolicy = new CustomerPolicy();
        customerPolicy.setId("CP1");
        customerPolicy.setUser(user);
        customerPolicy.setPolicy(policy);
        customerPolicy.setUserId("U1");
        customerPolicy.setPolicyId("P1");
        customerPolicy.setActive(true);
        customerPolicy.setCancelled(false);
        customerPolicy.setPremiumPaid(12000.0);
        customerPolicy.setPurchaseDate(LocalDate.now());
        customerPolicy.setRenewalDate(LocalDate.now().plusYears(1));
    }

    // ---- createCustomerPolicy ----
    @Test
    void testCreateCustomerPolicy_Success() {
        when(userRepository.findById("U1")).thenReturn(Optional.of(user));
        when(policyRepository.findById("P1")).thenReturn(Optional.of(policy));
        when(customerPolicyRepository.save(any(CustomerPolicy.class))).thenReturn(customerPolicy);

        CustomerPolicy result = service.createCustomerPolicy("U1", "P1");

        assertNotNull(result);
        assertEquals("U1", result.getUserId());
        assertEquals("P1", result.getPolicyId());
        assertTrue(result.isActive());
        assertFalse(result.isCancelled());
        assertEquals(12000.0, result.getPremiumPaid());
        verify(customerPolicyRepository, times(1)).save(any(CustomerPolicy.class));
    }

    @Test
    void testCreateCustomerPolicy_UserOrPolicyNotFound() {
        when(userRepository.findById("U1")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.createCustomerPolicy("U1", "P1"));

        assertEquals("User or Policy not found!", ex.getMessage());
    }

    // ---- getAllCustomerPolicies ----
    @Test
    void testGetAllCustomerPolicies() {
        when(customerPolicyRepository.findAll()).thenReturn(List.of(customerPolicy));

        List<CustomerPolicy> result = service.getAllCustomerPolicies();

        assertEquals(1, result.size());
        assertEquals("CP1", result.get(0).getId());
        verify(customerPolicyRepository, times(1)).findAll();
    }

    // ---- getCustomerPolicyById ----
    @Test
    void testGetCustomerPolicyById_Success() {
        when(customerPolicyRepository.findById("CP1")).thenReturn(Optional.of(customerPolicy));

        CustomerPolicy result = service.getCustomerPolicyById("CP1");

        assertNotNull(result);
        assertEquals("CP1", result.getId());
    }

    @Test
    void testGetCustomerPolicyById_NotFound() {
        when(customerPolicyRepository.findById("CP2")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.getCustomerPolicyById("CP2"));

        assertEquals("CustomerPolicy not found with id CP2", ex.getMessage());
    }

    // ---- update ----
    @Test
    void testUpdate_Success() {
        when(customerPolicyRepository.existsById("CP1")).thenReturn(true);
        when(customerPolicyRepository.save(any(CustomerPolicy.class))).thenReturn(customerPolicy);

        CustomerPolicy updated = service.update("CP1", customerPolicy);

        assertNotNull(updated);
        assertEquals("CP1", updated.getId());
        verify(customerPolicyRepository, times(1)).save(customerPolicy);
    }

    @Test
    void testUpdate_NotFound() {
        when(customerPolicyRepository.existsById("CP2")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.update("CP2", customerPolicy));

        assertEquals("CustomerPolicy not found with id CP2", ex.getMessage());
    }

    // ---- deleteCustomerPolicy ----
    @Test
    void testDeleteCustomerPolicy_Success() {
        when(customerPolicyRepository.findById("CP1")).thenReturn(Optional.of(customerPolicy));
        when(customerPolicyRepository.save(any(CustomerPolicy.class))).thenReturn(customerPolicy);

        CustomerPolicy result = service.deleteCustomerPolicy("CP1");

        assertTrue(result.isCancelled());
        assertFalse(result.isActive());
        verify(customerPolicyRepository, times(1)).save(customerPolicy);
    }

    @Test
    void testDeleteCustomerPolicy_NotFound() {
        when(customerPolicyRepository.findById("CP2")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.deleteCustomerPolicy("CP2"));

        assertEquals("Policy not found", ex.getMessage());
    }
}
