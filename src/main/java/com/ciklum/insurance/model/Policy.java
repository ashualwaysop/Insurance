package com.ciklum.insurance.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "policies")
public class Policy{
    @Id
    private String id;

    private String name;
    private String description;
    private String type; // Life, Health, Vehicle, Travel
    private double premium;
    private double coverageAmount;
}