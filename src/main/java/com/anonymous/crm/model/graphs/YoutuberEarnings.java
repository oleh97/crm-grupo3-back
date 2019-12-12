package com.anonymous.crm.model.graphs;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class YoutuberEarnings {
    private String channelName;
    private Double monetization;
    private Long views;
}
