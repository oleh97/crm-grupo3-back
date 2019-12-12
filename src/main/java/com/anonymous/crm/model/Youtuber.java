package com.anonymous.crm.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "youtubers")
public class Youtuber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String channelName;
    String channelLink;
    String email;
    String chosenPaymentMethod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "youtuberId", referencedColumnName = "id")
    YoutuberDetails youtuberDetails;

    @OneToMany(
            mappedBy = "youtuber",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Video> videos = new ArrayList<>();

    public void addVideo(Video video) {
        videos.add(video);
        video.setYoutuber(this);
    }
}
