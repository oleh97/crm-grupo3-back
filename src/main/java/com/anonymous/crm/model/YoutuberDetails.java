package com.anonymous.crm.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
@Table(name = "youtuberDetails")
public class YoutuberDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contentType;
    @ElementCollection(targetClass=Integer.class)
    private Map<String, Integer> ageRange;
    @ElementCollection(targetClass=Integer.class)
    private Map<String, Integer> geographicZone;
    private String visitRange;
    private Integer coursesSpending;
    private Double editionSpending;
    private Double miniatureSpending;
}

