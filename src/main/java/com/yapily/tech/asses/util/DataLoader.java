package com.yapily.tech.asses.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import javax.xml.crypto.Data;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.yapily.tech.asses.constant.ApplicationConstant.DATA;
import static com.yapily.tech.asses.constant.ApplicationConstant.RESULTS;


@Configuration
@DependsOn({"appConfig","restUtil"})
public class DataLoader {

    private static String endpoint;

    public static List<String> characterIdsList;

    private static RestUtil restUtil;

    private static ObjectMapper objectMapper;


    public DataLoader(@Value("${marvel_endpoint}") String endpoint, RestUtil restUtil){
        this.endpoint = endpoint;
        this.restUtil = restUtil;
        objectMapper = new ObjectMapper();
    }

   // @Bean
    public static List  getCharacterId() throws IOException {
        characterIdsList = new ArrayList();
        String url = endpoint;
        try{
            ResponseEntity<String> response = restUtil.getStringResponseEntity(url);
            if(response.getStatusCode().is2xxSuccessful()){
                JsonNode responseData = objectMapper.readTree(response.getBody());
                JsonNode dataNode = responseData.get(DATA);
                ArrayNode resultArray = (ArrayNode) dataNode.get(RESULTS);
                for (int i=0; i<resultArray.size();i++){
                    characterIdsList.add( resultArray.get(i).get("id").asText());
                }
            }
            String list = Arrays.toString(characterIdsList.toArray());
            FileWriter writer = new FileWriter("output.json");
            writer.write(list);
            writer.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
         return  null;
    }
}
