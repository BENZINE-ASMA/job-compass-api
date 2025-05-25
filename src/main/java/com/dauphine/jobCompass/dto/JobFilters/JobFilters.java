package com.dauphine.jobCompass.dto.JobFilters;

import java.util.UUID;

public final class JobFilters {
    private final String search;
    private final String category;
    private final String contractType;
    private final String location;
    private final UUID  ownerId;

    // Constructeur
    public JobFilters(String search, String category, String contractType, String location, UUID  ownerId) {
        this.search = search;
        this.category = category;
        this.contractType = contractType;
        this.location = location;
        this.ownerId = ownerId;
    }

    // Getters
    public String getSearch() { return this.search; }
    public String getCategory() { return this.category; }
    public String getContractType() { return this.contractType; }
    public String getLocation() { return this.location; }
    public UUID getOwnerId() { return this.ownerId; }


}
