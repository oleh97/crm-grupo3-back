package com.anonymous.crm.controller;

import com.anonymous.crm.model.Youtuber;
import com.anonymous.crm.model.YoutuberDto;
import com.anonymous.crm.model.graphs.CountriesVisualization;
import com.anonymous.crm.model.graphs.ServiceEarnings;
import com.anonymous.crm.model.graphs.YoutuberEarnings;
import com.anonymous.crm.service.YoutuberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
public class YoutuberController {

    @Autowired
    YoutuberService youtuberService;

    @PostMapping("/youtuber")
    public @ResponseBody
    ResponseEntity<Youtuber> addYoutuber(@RequestBody Youtuber youtuber) {
        return new ResponseEntity<>(youtuberService.saveYoutuber(youtuber), HttpStatus.CREATED);
    }

    @GetMapping("/youtubers/campaign")
    public @ResponseBody
    ResponseEntity<List<Youtuber>> campaign(@RequestParam String contentType, @RequestParam String geographicZone,
                                            @RequestParam String ageRange, @RequestParam String visitRange) {
         return new ResponseEntity<>(youtuberService.campaign(contentType, geographicZone, ageRange, visitRange), HttpStatus.OK);
    }

    @GetMapping("/youtuber/{id}/earnings-graph")
    public @ResponseBody
    ResponseEntity<Map<Integer, Double>> youtuberEarningsGraph(@RequestParam Integer month, @PathVariable Integer id){
        return new ResponseEntity<>(youtuberService.getYoutuberEarningsGraph(month, id), HttpStatus.OK);
    }

    @GetMapping("/youtuber/{id}/services-spending-graph")
    public @ResponseBody
    ResponseEntity<Map<String, Double>> youtuberSpendingGraph(@PathVariable Integer id){
        return new ResponseEntity<>(youtuberService.getYoutuberSpending(id), HttpStatus.OK);
    }

    @GetMapping("/youtuber/{id}/age-range-graph")
    public @ResponseBody
    ResponseEntity<Map<String, Integer>> youtuberAgeRangeGraph(@PathVariable Integer id){
        return new ResponseEntity<>(youtuberService.getYoutuberAgeRange(id), HttpStatus.OK);
    }

    @GetMapping("/youtuber/{id}/countries-graph")
    public @ResponseBody
    ResponseEntity<Map<String, Integer>> youtuberCountriesGraph(@PathVariable Integer id){
        return new ResponseEntity<>(youtuberService.getYoutuberCountries(id), HttpStatus.OK);
    }

    @GetMapping("/youtubers/company-income")
    public @ResponseBody
    ResponseEntity<Map<Integer, Double>> montlyCompanyIncome() {
        return new ResponseEntity<>(youtuberService.getCompanyProfits(), HttpStatus.OK);
    }

    @GetMapping("/youtubers/top")
    public @ResponseBody
    ResponseEntity<List<YoutuberEarnings>> getTopYoutubers() {
        return new ResponseEntity<>(youtuberService.getTopYoutubers(), HttpStatus.OK);
    }

    @GetMapping("/youtubers/services")
    public @ResponseBody
    ResponseEntity<ServiceEarnings> getCompanyServiceEarnings() {
        return new ResponseEntity<>(youtuberService.getCompanyServiceEarnings(), HttpStatus.OK);
    }

    @GetMapping("/youtubers/countries-visualization")
    public @ResponseBody
    ResponseEntity<CountriesVisualization> getCompanyTopCountriesVisualization() {
        return new ResponseEntity<>(youtuberService.getCompanyTopCountriesVisualization(), HttpStatus.OK);
    }

    @GetMapping("/youtubers/all/summarized")
    public @ResponseBody
    ResponseEntity<List<YoutuberDto>> getAllYoutubers() {
        return new ResponseEntity<>(youtuberService.getAllYoutubers(), HttpStatus.OK);
    }
}
