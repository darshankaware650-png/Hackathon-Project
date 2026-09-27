package com.hackathon.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import com.hackathon.model.Opportunity;

@Repository
public class OpportunityRepository {

    private static final String COLLECTION = "opportunities";

    private final boolean useFirestore;
    private final Map<String, Opportunity> memoryStore = new ConcurrentHashMap<>();
    private Firestore firestore;

    public OpportunityRepository() {
        this.useFirestore = !FirebaseApp.getApps().isEmpty();

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
                firestore.collection(COLLECTION)
                        .document(opportunity.getId())
                        .set(opportunity)
                        .get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Saving opportunity was interrupted", e);
            } catch (ExecutionException e) {
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
                CollectionReference collection = firestore.collection(COLLECTION);
                List<Opportunity> results = new ArrayList<>();

                for (QueryDocumentSnapshot document :
                        collection.get().get().getDocuments()) {
                    results.add(document.toObject(Opportunity.class));
                }

                return results;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Fetching opportunities was interrupted", e);
            } catch (ExecutionException e) {
                throw new RuntimeException("Failed to fetch opportunities", e);
            }
        }

        return new ArrayList<>(memoryStore.values());
    }

    public Optional<Opportunity> findById(String id) {
        if (useFirestore) {
            try {
                var document = firestore.collection(COLLECTION)
                        .document(id)
                        .get()
                        .get();

                if (document.exists()) {
                    return Optional.ofNullable(
                            document.toObject(Opportunity.class)
                    );
                }

                return Optional.empty();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Fetching opportunity was interrupted", e);
            } catch (ExecutionException e) {
                throw new RuntimeException("Failed to fetch opportunity", e);
            }
        }

        return Optional.ofNullable(memoryStore.get(id));
    }

    public boolean isEmpty() {
        return findAll().isEmpty();
    }
}