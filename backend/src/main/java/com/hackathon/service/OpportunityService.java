package com.hackathon.service;

import com.hackathon.model.Opportunity;
import com.hackathon.repository.OpportunityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OpportunityService {

    private final OpportunityRepository repository;

    public OpportunityService(OpportunityRepository repository) {
        this.repository = repository;
    }

    public Opportunity create(Opportunity opportunity) {
        return repository.save(opportunity);
    }

    public List<Opportunity> findAll(String category, String search) {
        return repository.findAll().stream()
                .filter(o -> category == null || category.isBlank()
                        || (o.getCategory() != null && o.getCategory().equalsIgnoreCase(category)))
                .filter(o -> search == null || search.isBlank() || matchesSearch(o, search))
                .toList();
    }

    private boolean matchesSearch(Opportunity o, String search) {
        String s = search.toLowerCase();
        if (o.getTitle() != null && o.getTitle().toLowerCase().contains(s)) return true;
        if (o.getDescription() != null && o.getDescription().toLowerCase().contains(s)) return true;
        if (o.getTags() != null) {
            for (String tag : o.getTags()) {
                if (tag.toLowerCase().contains(s)) return true;
            }
        }
        return false;
    }

    public Optional<Opportunity> findById(String id) {
        return repository.findById(id);
    }

    public List<Opportunity> recommendFor(List<String> skills, List<String> interests) {
        List<String> combined = new ArrayList<>();
        if (skills != null) combined.addAll(skills);
        if (interests != null) combined.addAll(interests);
        List<String> lower = combined.stream().map(String::toLowerCase).toList();

        return repository.findAll().stream()
                .filter(o -> o.getTags() != null && o.getTags().stream()
                        .anyMatch(tag -> lower.contains(tag.toLowerCase())))
                .toList();
    }

    public boolean isEmpty() {
        return repository.isEmpty();
    }
}
