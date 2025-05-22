package com.dauphine.jobCompass.dto.JobFilters;

public final class JobFilters {
    private final String search;
    private final String category;
    private final String contractType;
    private final String location;

    // Constructeur
    public JobFilters(String search, String category, String contractType, String location) {
        this.search = search;
        this.category = category;
        this.contractType = contractType;
        this.location = location;
    }

    // Getters
    public String getSearch() { return this.search; }
    public String getCategory() { return this.category; }
    public String getContractType() { return this.contractType; }
    public String getLocation() { return this.location; }


}
