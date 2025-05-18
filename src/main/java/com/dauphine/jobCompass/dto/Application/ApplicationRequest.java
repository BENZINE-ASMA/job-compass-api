package com.dauphine.jobCompass.dto.Application;

import java.util.UUID;

public class ApplicationRequest {
    public static class SimpleJobDTO {
        private UUID id;
        private String title;
        private String location;
        private String companyName;

        // Getters et setters

        public UUID getId() {
            return id;
        }
        public void setId(UUID id) {
            this.id = id;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getLocation() {
            return location;
        }
        public void setLocation(String location) {
            this.location = location;
        }
        public String getCompanyName() {
            return companyName;
        }
        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
    }
}
