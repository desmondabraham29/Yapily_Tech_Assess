package com.yapily.tech.asses.util;

import com.yapily.tech.asses.config.ApplicationConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static com.yapily.tech.asses.constant.ApplicationConstant.*;

@Component(value="restUtil")
public class RestUtil {

    private ApplicationConfig applicationConfig;

    private RestTemplate restTemplate;

    private  static String API_VALUE;

    private static String HASH_VALUE;



    public RestUtil(ApplicationConfig applicationConfig,RestTemplate restTemplate){
        this.applicationConfig = applicationConfig;
        this.restTemplate = restTemplate;
        this.API_VALUE = applicationConfig.getApiKey();
        this.HASH_VALUE = applicationConfig.getHashValue();
    }

    public ResponseEntity<String> getStringResponseEntity(String url) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(TS, "1")
                .queryParam(API_KEY, this.API_VALUE)
                .queryParam(HASH, this.HASH_VALUE);
        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                httpEntity,
                String.class);
    }
}
