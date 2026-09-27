package com.hackathon.controller;

import com.hackathon.model.Opportunity;
import com.hackathon.model.StudentProfile;
import com.hackathon.service.OpportunityService;
import com.hackathon.service.ProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final OpportunityService opportunityService;

    public ProfileController(ProfileService profileService, OpportunityService opportunityService) {
        this.profileService = profileService;
        this.opportunityService = opportunityService;
    }

    @PostMapping
    public StudentProfile create(@RequestBody StudentProfile profile) {
        return profileService.save(profile);
    }

    @GetMapping("/{id}")
    public StudentProfile get(@PathVariable String id) {
        return profileService.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    @PostMapping("/{id}/bookmark/{opportunityId}")
    public StudentProfile bookmark(@PathVariable String id, @PathVariable String opportunityId) {
        return profileService.addBookmark(id, opportunityId);
    }

    @DeleteMapping("/{id}/bookmark/{opportunityId}")
    public StudentProfile unbookmark(@PathVariable String id, @PathVariable String opportunityId) {
        return profileService.removeBookmark(id, opportunityId);
    }

    @GetMapping("/{id}/bookmarks")
    public List<Opportunity> bookmarks(@PathVariable String id) {
        StudentProfile profile = profileService.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
        return profile.getBookmarks().stream()
                .map(oid -> opportunityService.findById(oid).orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }
}
