package com.example.COVIDtracker.modules;

public class CountryData {

    private String country;
    private int newCases;
    private int newCasesPerMillion;
    private int newDeaths;
    private int totalDeathsPerMillion;
    private int peopleVaccinated;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTotalDeathsPerMillion() {
        return totalDeathsPerMillion;
    }

    public void setTotalDeathsPerMillion(int totalDeathsPerMillion) {
        this.totalDeathsPerMillion = totalDeathsPerMillion;
    }

    public int getNewCases() {
        return newCases;
    }

    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(int newDeaths) {
        this.newDeaths = newDeaths;
    }

    public int getNewCasesPerMillion() {
        return newCasesPerMillion;
    }

    public void setNewCasesPerMillion(int newCasesPerMillion) {
        this.newCasesPerMillion = newCasesPerMillion;
    }

    public int getPeopleVaccinated() {
        return peopleVaccinated;
    }

    public void setPeopleVaccinated(int peopleVaccinated) {
        this.peopleVaccinated = peopleVaccinated;
    }

}
