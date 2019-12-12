package com.anonymous.crm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_youtuber")
    @JsonIgnore
    private Youtuber youtuber;

    private Double monetization;
    LocalDate videoUploadDate;
    private String name;
    private Integer views;

}
