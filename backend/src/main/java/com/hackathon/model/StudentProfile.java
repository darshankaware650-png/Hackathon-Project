package com.hackathon.model;

import java.util.ArrayList;
import java.util.List;

public class StudentProfile {
    private String id;
    private String name;
    private String education;
    private List<String> skills = new ArrayList<>();
    private List<String> interests = new ArrayList<>();
    private List<String> preferredCategories = new ArrayList<>();
    private List<String> bookmarks = new ArrayList<>();

    public StudentProfile() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public List<String> getInterests() { return interests; }
    public void setInterests(List<String> interests) { this.interests = interests; }

    public List<String> getPreferredCategories() { return preferredCategories; }
    public void setPreferredCategories(List<String> preferredCategories) { this.preferredCategories = preferredCategories; }

    public List<String> getBookmarks() { return bookmarks; }
    public void setBookmarks(List<String> bookmarks) { this.bookmarks = bookmarks; }
}
