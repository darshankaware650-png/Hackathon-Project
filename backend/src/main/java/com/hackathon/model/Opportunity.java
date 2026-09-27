package com.hackathon.model;

import java.util.List;

public class Opportunity {
    private String id;
    private String title;
    private String category; // Hackathon, Internship, Scholarship, Certification, Competition, Workshop, Course
    private String description;
    private String link;
    private String deadline;
    private List<String> tags;

    public Opportunity() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}
