package com.hackathon.repository;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.hackathon.model.Opportunity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@Repository
public class OpportunityRepository {

    private static final String COLLECTION = "opportunities";

    private final boolean useFirestore;
    private final Map<String, Opportunity> memoryStore = new ConcurrentHashMap<>();
    private Firestore firestore;

    public OpportunityRepository(Boolean firebaseInitialized) {
        this.useFirestore = Boolean.TRUE.equals(firebaseInitialized);
        if (this.useFirestore) {
            this.firestore = FirestoreClient.getFirestore();
        }
    }

    public Opportunity save(Opportunity opportunity) {
        if (opportunity.getId() == null || opportunity.getId().isBlank()) {
            opportunity.setId(UUID.randomUUID().toString());
        }
        if (useFirestore) {
            try {
                firestore.collection(COLLECTION).document(opportunity.getId()).set(opportunity).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Failed to save opportunity", e);
            }
        } else {
            memoryStore.put(opportunity.getId(), opportunity);
        }
        return opportunity;
    }

    public List<Opportunity> findAll() {
        if (useFirestore) {
            try {
                CollectionReference col = firestore.collection(COLLECTION);
                List<Opportunity> results = new ArrayList<>();
                for (QueryDocumentSnapshot doc : col.get().get().getDocuments()) {
                    results.add(doc.toObject(Opportunity.class));
                }
                return results;
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Failed to fetch opportunities", e);
            }
        }
        return new ArrayList<>(memoryStore.values());
    }

    public Optional<Opportunity> findById(String id) {
        if (useFirestore) {
            try {
                var doc = firestore.collection(COLLECTION).document(id).get().get();
                if (doc.exists()) {
                    return Optional.ofNullable(doc.toObject(Opportunity.class));
                }
                return Optional.empty();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Failed to fetch opportunity", e);
            }
        }
        return Optional.ofNullable(memoryStore.get(id));
    }

    public boolean isEmpty() {
        return findAll().isEmpty();
    }
}
