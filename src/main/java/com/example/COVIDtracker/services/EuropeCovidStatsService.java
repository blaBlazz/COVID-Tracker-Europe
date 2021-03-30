package com.example.COVIDtracker.services;

import com.example.COVIDtracker.modules.CountryData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.time.*;
import java.time.temporal.ChronoUnit;


@Service
public class EuropeCovidStatsService {

    private static String STATS_URL = "https://raw.githubusercontent.com/owid/covid-19-data/master/public/data/owid-covid-data.csv";

    private Instant now = Instant.now();
    private Instant yesterday = now.minus(1, ChronoUnit.DAYS);
    LocalDateTime ldt = LocalDateTime.ofInstant(yesterday, ZoneId.systemDefault());
    private String date = String.format("%s %d %d%n", ldt.getMonth(), ldt.getDayOfMonth(), ldt.getYear());
    public String getDate() { return date; }

    private List<CountryData> statsFull = new ArrayList<>();

    public List<CountryData> getStatsFull() { return statsFull; }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void obtainVirusData() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(STATS_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);


        for (CSVRecord record : records) {
            CountryData locationStat = new CountryData();
            String cont = record.get("continent");
            String date = record.get("date");
            if (Objects.equals(cont, "Europe") && Objects.equals(date, yesterday.toString().substring(0,10))) {

                locationStat.setCountry(record.get("location"));

                String newCases = record.get("new_cases");
                String newDeaths = record.get("new_deaths");
                String newCasesPerMillion = record.get("new_cases_per_million");
                String totalDeathsPerMillion = record.get("total_deaths_per_million");
                String peopleVaccinated = record.get("people_vaccinated");
                if (!newCases.isEmpty()) { locationStat.setNewCases((int) Double.parseDouble(newCases)); }
                if (!newDeaths.isEmpty()) { locationStat.setNewDeaths((int) Double.parseDouble(newDeaths)); }
                if (!newCasesPerMillion.isEmpty()) { locationStat.setNewCasesPerMillion((int) Double.parseDouble(newCasesPerMillion)); }
                if (!totalDeathsPerMillion.isEmpty()) { locationStat.setTotalDeathsPerMillion((int) Double.parseDouble(totalDeathsPerMillion)); }
                if (!peopleVaccinated.isEmpty()) { locationStat.setPeopleVaccinated((int) Double.parseDouble(peopleVaccinated)); }

                statsFull.add(locationStat);
            }
        }
    }
}
