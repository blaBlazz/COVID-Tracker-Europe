package com.example.COVIDtracker.controllers;

import com.example.COVIDtracker.modules.CountryData;
import com.example.COVIDtracker.services.EuropeCovidStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class TrackerController {

    @Autowired
    EuropeCovidStatsService covidDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<CountryData> statsFull = covidDataService.getStatsFull();
        String date = covidDataService.getDate();
        int totalNewCases = statsFull.stream().mapToInt(stat -> stat.getNewCases()).sum();
        int totalNewDeaths = statsFull.stream().mapToInt(stat -> stat.getNewDeaths()).sum();
        int totalVaccinated = statsFull.stream().mapToInt(stat -> stat.getPeopleVaccinated()).sum();
        model.addAttribute("cases", statsFull);
        if (totalVaccinated == 0) {
            model.addAttribute("totalVaccinated", "N/A");
        } else {
            model.addAttribute("totalVaccinated", totalVaccinated);
        }
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("totalNewDeaths", totalNewDeaths);
        model.addAttribute("date", date);
        return "tracker";
    }
}
