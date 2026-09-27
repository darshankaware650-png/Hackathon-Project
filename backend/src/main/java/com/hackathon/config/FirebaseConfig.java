import java.io.FileInputStream;

// ...

String credentialsPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

if (credentialsPath == null || credentialsPath.isBlank()) {
    log.warn("GOOGLE_APPLICATION_CREDENTIALS not set - using in-memory store.");
    return false;
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
    return true;
} catch (Exception e) {
    log.error("Failed to initialize Firebase, falling back to in-memory store: {}", e.getMessage());
    return false;
}