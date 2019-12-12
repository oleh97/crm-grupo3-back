package com.anonymous.crm.service;

import com.anonymous.crm.model.Video;
import com.anonymous.crm.model.Youtuber;
import com.anonymous.crm.model.YoutuberDetails;
import com.anonymous.crm.types.AgeRange;
import com.anonymous.crm.types.ContentType;
import com.anonymous.crm.types.GeographicZone;
import com.anonymous.crm.types.VisitRange;
import com.anonymous.crm.utils.Randomizer;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class YoutubeAPI {

    private static Integer MIN_RANGE = 50;
    private static Integer MAX_RANGE = 200;
    private static Double MAX_MONETIZATION_RANGE = 0.0005;
    private static Double MIN_MONETIZATION_RANGE = 0.0002;

    private Random random = new Random();
    private DecimalFormat format = new DecimalFormat("#0.000");

    YoutuberDetails getYoutuberData(Youtuber youtuber) {
        YoutuberDetails details = new YoutuberDetails();
        details.setContentType(Randomizer.randomEnum(ContentType.class).getAgeRange());
        Map<String, Integer> ageRangeMap = Randomizer.randomPercentage(AgeRange.class);
        Map<String, Integer> ageRangeMapModified = new HashMap<>();
        ageRangeMap.keySet().forEach(elem ->
                ageRangeMapModified.put(AgeRange.valueOf(elem).getAgeRange(), ageRangeMap.get(elem))
        );
        details.setAgeRange(ageRangeMapModified);
        Map<String, Integer> geographicZoneMap = Randomizer.randomPercentage(GeographicZone.class);
        Map<String, Integer> geographicZoneMapModified = new HashMap<>();
        geographicZoneMap.keySet().forEach(elem ->
                geographicZoneMapModified.put(GeographicZone.valueOf(elem).getCountry(), geographicZoneMap.get(elem))
        );
        details.setGeographicZone(geographicZoneMapModified);
        details.setVisitRange(Randomizer.randomEnum(VisitRange.class).getVisitRange());
        return details;
    }

    void getYoutuberVideos(Youtuber youtuber) {
        Integer numVideos = random.nextInt(MAX_RANGE - MIN_RANGE) + MIN_RANGE;
        for(int i = 1; i <= numVideos; i++) {
            Video v = new Video();
            v.setVideoUploadDate(Randomizer.generateRandomDate());
            v.setName("Video " + i);
            v.setViews(getVideoViews(youtuber));
            Double generatedMoney = v.getViews() * getMonetizationFactor(youtuber);
            v.setMonetization(Double.parseDouble(format.format(generatedMoney).replace(',', '.')));
            youtuber.addVideo(v);
        }
    }

    private Integer getVideoViews(Youtuber youtuber) {
        switch (VisitRange.fromString(youtuber.getYoutuberDetails().getVisitRange())) {
            case LOW1:
                return random.nextInt(1000 - 1) + 1;
            case LOW2:
                return random.nextInt(5000 - 1000) + 1000;
            case LOW3:
                return random.nextInt(10000 - 5000) + 5000;
            case MID1:
                return random.nextInt(20000 - 10000) + 10000;
            case MID2:
                return random.nextInt(500000 - 20000) + 20000;
            case MID3:
                return random.nextInt(100000 - 50000) + 50000;
            case HIGH1:
                return random.nextInt(200000 - 100000) + 100000;
            case HIGH2:
                return random.nextInt(500000 - 200000) + 200000;
            case HIGH3:
                return random.nextInt(1000000 - 500000) + 500000;
            case SUPER:
                return random.nextInt(50000000 - 1000000) + 1000000;
        }
        return -1;
    }

    private Double getMonetizationFactor(Youtuber youtuber){
        return ThreadLocalRandom.current().nextDouble(MIN_MONETIZATION_RANGE, MAX_MONETIZATION_RANGE);
    }

}
