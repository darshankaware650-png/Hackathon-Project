package com.hackathon.repository;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.hackathon.model.StudentProfile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@Repository
public class ProfileRepository {

    private static final String COLLECTION = "profiles";

    private final boolean useFirestore;
    private final Map<String, StudentProfile> memoryStore = new ConcurrentHashMap<>();
    private Firestore firestore;

    public ProfileRepository(Boolean firebaseInitialized) {
        this.useFirestore = Boolean.TRUE.equals(firebaseInitialized);
        if (this.useFirestore) {
            this.firestore = FirestoreClient.getFirestore();
        }
    }

    public StudentProfile save(StudentProfile profile) {
        if (profile.getId() == null || profile.getId().isBlank()) {
            profile.setId(UUID.randomUUID().toString());
        }
        if (useFirestore) {
            try {
                firestore.collection(COLLECTION).document(profile.getId()).set(profile).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Failed to save profile", e);
            }
        } else {
            memoryStore.put(profile.getId(), profile);
        }
        return profile;
    }

    public Optional<StudentProfile> findById(String id) {
        if (useFirestore) {
            try {
                var doc = firestore.collection(COLLECTION).document(id).get().get();
                if (doc.exists()) {
                    return Optional.ofNullable(doc.toObject(StudentProfile.class));
                }
                return Optional.empty();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Failed to fetch profile", e);
            }
        }
        return Optional.ofNullable(memoryStore.get(id));
    }
}
