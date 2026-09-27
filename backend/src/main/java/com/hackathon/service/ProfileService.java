package com.hackathon.service;

import com.hackathon.model.StudentProfile;
import com.hackathon.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository repository;

    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    public StudentProfile save(StudentProfile profile) {
        return repository.save(profile);
    }

    public Optional<StudentProfile> findById(String id) {
        return repository.findById(id);
    }

    public StudentProfile addBookmark(String profileId, String opportunityId) {
        StudentProfile profile = repository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        if (!profile.getBookmarks().contains(opportunityId)) {
            profile.getBookmarks().add(opportunityId);
        }
        return repository.save(profile);
    }

    public StudentProfile removeBookmark(String profileId, String opportunityId) {
        StudentProfile profile = repository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.getBookmarks().remove(opportunityId);
        return repository.save(profile);
    }
}
