package com.ciklum.insurance.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "customer_policies")
public class CustomerPolicy {
    @Id
    private String id;

    // ⚠️ use camelCase 'userId' (NOT 'UserId')
    private String userId;      // Link to User
    private String policyId;    // Link to Policy

    private LocalDate purchaseDate;
    private LocalDate renewalDate;

    private boolean active;     // Policy status
    private boolean cancelled;  // Cancel flag

    private double premiumPaid; // Paid amount

    // Optional embedded documents (will be stored in Mongo as nested docs)
    private User user;
    private Policy policy;


}
