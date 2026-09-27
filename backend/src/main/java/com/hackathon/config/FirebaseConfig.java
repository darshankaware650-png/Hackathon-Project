package com.hackathon.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {
    private static final Logger log = LoggerFactory.getLogger(FirebaseConfig.class);

    public FirebaseConfig() {
        String credentialsPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

        if (credentialsPath == null || credentialsPath.isBlank()) {
            log.warn("GOOGLE_APPLICATION_CREDENTIALS not set - using in-memory store.");
            return;
        }

        try {
            if (FirebaseApp.getApps().isEmpty()) {
                GoogleCredentials credentials =
                        GoogleCredentials.fromStream(new FileInputStream(credentialsPath));

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(credentials)
                        .build();

                FirebaseApp.initializeApp(options);
                log.info("Firebase initialized - using Firestore data store.");
            }
        } catch (Exception e) {
            log.error("Failed to initialize Firebase: {}", e.getMessage());
        }
    }
}