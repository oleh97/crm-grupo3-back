package com.anonymous.crm.service;

import com.anonymous.crm.model.Video;
import com.anonymous.crm.model.Youtuber;
import com.anonymous.crm.model.YoutuberDto;
import com.anonymous.crm.model.graphs.CountriesVisualization;
import com.anonymous.crm.model.graphs.ServiceEarnings;
import com.anonymous.crm.model.graphs.YoutuberEarnings;
import com.anonymous.crm.repository.VideoRepository;
import com.anonymous.crm.repository.YoutuberRepository;
import com.anonymous.crm.utils.MonthMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class YoutuberService {

    public static final Integer COURSE_PRICE = 50;
    public static final Integer EDITION_PRICE_MIN = 25;
    public static final Integer EDITION_PRICE_MAX = 150;

    public static final Integer MINIATURE_PRICE_MIN = 10;
    public static final Integer MINIATURE_PRICE_MAX = 35;

    private DecimalFormat format = new DecimalFormat("#0.000");

    @Autowired
    YoutuberRepository youtuberRepository;

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    YoutubeAPI youtubeAPI;

    public Youtuber saveYoutuber(Youtuber youtuber) {
        youtuber.setYoutuberDetails(youtubeAPI.getYoutuberData(youtuber));
        youtubeAPI.getYoutuberVideos(youtuber);
        youtuber.getYoutuberDetails().setCoursesSpending(ThreadLocalRandom.current().nextInt(1, 5) * COURSE_PRICE);
        youtuber.getYoutuberDetails().setEditionSpending(
                ThreadLocalRandom.current().nextInt(1, youtuber.getVideos().size()) *
                        ThreadLocalRandom.current().nextDouble(EDITION_PRICE_MIN, EDITION_PRICE_MAX)
        );
        youtuber.getYoutuberDetails().setMiniatureSpending(
                ThreadLocalRandom.current().nextInt(1, youtuber.getVideos().size()) *
                        ThreadLocalRandom.current().nextDouble(MINIATURE_PRICE_MIN, MINIATURE_PRICE_MAX)
        );
        return youtuberRepository.save(youtuber);
    }

    public List<Youtuber> campaign(String contenType, String geographicZone, String ageRange, String visitRange) {
        return youtuberRepository.getYoutubersForCampaign(contenType, geographicZone, ageRange, visitRange);
    }

    public Map<Integer, Double> getYoutuberEarningsGraph(Integer month, Integer youtuberId) {
        Map<Integer, Double> monthMonetization = MonthMap.getMonthMap(MonthMap.monthMaxDay(month));
        Youtuber youtuber = youtuberRepository.getOne(youtuberId);
        List<Video> monthVideos = youtuber.getVideos().stream()
                .filter(elem -> elem.getVideoUploadDate().getMonthValue() == month)
                .collect(Collectors.toList());
        monthVideos.forEach(elem -> {
            monthMonetization.put(elem.getVideoUploadDate().getDayOfMonth(),
                    Double.parseDouble(format.format(monthMonetization.get(elem.getVideoUploadDate().getDayOfMonth())).replace(',', '.')) +
                            Double.parseDouble(format.format(elem.getMonetization()).replace(',', '.')));
        });
        return monthMonetization;
    }

    public Map<String, Double> getYoutuberSpending(Integer youtuberId) {
        Map<String, Double> annualSpending = new HashMap<>();
        Youtuber youtuber = youtuberRepository.getOne(youtuberId);
        annualSpending.put("courses", (double)youtuber.getYoutuberDetails().getCoursesSpending());
        annualSpending.put("edition", Double.parseDouble(format.format(youtuber.getYoutuberDetails().getEditionSpending()).replace(',', '.')));
        annualSpending.put("minuatures", Double.parseDouble(format.format(youtuber.getYoutuberDetails().getMiniatureSpending()).replace(',', '.')));
        return annualSpending;
    }

    public Map<String, Integer> getYoutuberAgeRange(Integer youtuberId){
        Youtuber youtuber = youtuberRepository.getOne(youtuberId);
        return youtuber.getYoutuberDetails().getAgeRange();
    }

    public Map<String, Integer> getYoutuberCountries(Integer youtuberId){
        Youtuber youtuber = youtuberRepository.getOne(youtuberId);
        return youtuber.getYoutuberDetails().getGeographicZone();
    }

    public Map<Integer, Double> getCompanyProfits() {
        Map<Integer, Double> companyProfits = MonthMap.getMonthsMap();
        companyProfits.forEach((k,v) -> {
            LocalDate start = LocalDate.of(2019, k, 1);
            LocalDate end = LocalDate.of(2019, k, MonthMap.monthMaxDay(k));
            Double monthProfit = Double.parseDouble(format.format(videoRepository.sumAllVideosProfitInMonth(start, end) *0.2).replace(',', '.'));
            companyProfits.put(k, monthProfit);
        });
        return companyProfits;
    }

    public List<YoutuberEarnings> getTopYoutubers() {
        List<YoutuberEarnings> topYoutubers = youtuberRepository.getTopYoutubers(PageRequest.of(0,5));
        topYoutubers.forEach(elem -> {
            Double d = Double.parseDouble(format.format(elem.getMonetization()).replace(',', '.'));
            elem.setMonetization(d);
        });
        return topYoutubers;
    }

    public ServiceEarnings getCompanyServiceEarnings() {
        ServiceEarnings serviceEarnings = youtuberRepository.getCompanyEarningsWithServices();
        serviceEarnings.setEdition(Double.parseDouble(format.format(serviceEarnings.getEdition()).replace(',', '.')));
        serviceEarnings.setMinuatures(Double.parseDouble(format.format(serviceEarnings.getMinuatures()).replace(',', '.')));
        return serviceEarnings;
    }

    public CountriesVisualization getCompanyTopCountriesVisualization() {
        CountriesVisualization countriesVisualization = youtuberRepository.getCompanyCountriesVisualization();
        Long totalSum = 0L;
        countriesVisualization.setSpain(countriesVisualization.getSpain() / countriesVisualization.getTotalYoutubers());
        totalSum += countriesVisualization.getSpain();
        countriesVisualization.setFrance(countriesVisualization.getFrance() / countriesVisualization.getTotalYoutubers());
        totalSum += countriesVisualization.getFrance();
        countriesVisualization.setAndorra(countriesVisualization.getAndorra() / countriesVisualization.getTotalYoutubers());
        totalSum += countriesVisualization.getAndorra();
        countriesVisualization.setUnitedKingdom(countriesVisualization.getUnitedStatesOfAmerica() / countriesVisualization.getTotalYoutubers());
        totalSum += countriesVisualization.getUnitedKingdom();
        countriesVisualization.setUnitedStatesOfAmerica(countriesVisualization.getUnitedStatesOfAmerica() / countriesVisualization.getTotalYoutubers());
        totalSum += countriesVisualization.getUnitedStatesOfAmerica();
        countriesVisualization.setSouthAmerica(countriesVisualization.getSouthAmerica() / countriesVisualization.getTotalYoutubers());
        totalSum += countriesVisualization.getSouthAmerica();
        countriesVisualization.setChina(countriesVisualization.getChina() / countriesVisualization.getTotalYoutubers());
        totalSum += countriesVisualization.getChina();
        countriesVisualization.setJapan(countriesVisualization.getJapan() / countriesVisualization.getTotalYoutubers());
        totalSum += countriesVisualization.getJapan();
        Long difference;
        if(totalSum > 100) {
            difference = totalSum - 100;
            countriesVisualization.setSpain(countriesVisualization.getSpain() - difference);
        }
        else if(totalSum < 100) {
            difference = 100 - totalSum;
            countriesVisualization.setSpain(countriesVisualization.getSpain() + difference);
        }
        return countriesVisualization;
    }

    public List<YoutuberDto> getAllYoutubers() {
        List<YoutuberDto> youtuberDtos = new ArrayList<>();
        youtuberRepository.findAll().forEach(elem -> {
            youtuberDtos.add(new YoutuberDto(elem.getChannelName(), (long)elem.getId()));
        });
        return youtuberDtos;
    }

}
