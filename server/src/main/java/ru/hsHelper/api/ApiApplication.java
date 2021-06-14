package ru.hsHelper.api;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.jetbrains.annotations.NotNull;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.hsHelper.api.configs.FcmSettings;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;

@SpringBootApplication
public class ApiApplication {
    private static final String APPLICATION_NAME = "HSHelper";

    private final FcmSettings fcmSettings;

    @Autowired
    public ApiApplication(FcmSettings fcmSettings) {
        this.fcmSettings = fcmSettings;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    @SuppressWarnings("deprecation")
    GoogleCredential googleCredential() throws IOException {
        InputStream credentialInput = ApiApplication.class.getResourceAsStream(fcmSettings.getServiceAccountFile());
        assert credentialInput != null;
        return GoogleCredential.fromStream(credentialInput);
    }

    @Bean
    @SuppressWarnings("deprecation")
    Drive drive(@NotNull GoogleCredential googleCredential) throws GeneralSecurityException, IOException {
        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = googleCredential.createScoped(DriveScopes.all());
        return new Drive.Builder(httpTransport, jsonFactory, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    @Bean
    @SuppressWarnings("deprecation")
    Sheets sheets(@NotNull GoogleCredential googleCredential) throws GeneralSecurityException, IOException {
        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = googleCredential.createScoped(List.of(SheetsScopes.SPREADSHEETS_READONLY));
        return new Sheets.Builder(httpTransport, jsonFactory, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        InputStream credentialInput = ApiApplication.class.getResourceAsStream(fcmSettings.getServiceAccountFile());
        assert credentialInput != null;
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(credentialInput);
        FirebaseOptions firebaseOptions = FirebaseOptions
            .builder()
            .setCredentials(googleCredentials)
            .build();
        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, APPLICATION_NAME);
        return FirebaseMessaging.getInstance(app);
    }

    @Bean
    RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://redis:6381");
        return Redisson.create(config);
    }
}
