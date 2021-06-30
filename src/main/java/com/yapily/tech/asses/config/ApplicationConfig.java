package com.yapily.tech.asses.config;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration(value="appConfig")
public class ApplicationConfig {

    private String bucketName;
    private String apiKeyFile;
    private String hashFile;

    public ApplicationConfig(@Value("${bucket_name}") String bucketName,
                             @Value("${api_key_file_name}") String apiKeyFile,
                             @Value("${hash_value_file_name}") String hashFile
                             ){
        this.bucketName = bucketName;
        this.apiKeyFile = apiKeyFile;
        this.hashFile = hashFile;
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean("googleCloudStorage")
    public Storage googleCloudStorage(){
        return StorageOptions.getDefaultInstance().getService();
    }

    @DependsOn("googleCloudStorage")
    @Bean
    public String getApiKey(){
        BlobId blobId = BlobId.of(bucketName, apiKeyFile);
        Storage storage = googleCloudStorage();
        byte[] content = storage.readAllBytes(blobId);
        String contentString = new String(content, UTF_8);
        return contentString;
    }

    @DependsOn("googleCloudStorage")
    @Bean
    public String getHashValue(){
        BlobId blobId = BlobId.of(bucketName, hashFile);
        Storage storage = googleCloudStorage();
        byte[] content = storage.readAllBytes(blobId);
        String contentString = new String(content, UTF_8);
        return contentString;
    }
}
