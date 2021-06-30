package com.yapily.tech.asses.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.yapily.tech.asses.dto.CharacterInfo;
import com.yapily.tech.asses.dto.Thumbnail;
import com.yapily.tech.asses.exception.ApplicationException;
import com.yapily.tech.asses.exception.ServiceException;
import com.yapily.tech.asses.util.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;


import static com.yapily.tech.asses.constant.ApplicationConstant.*;

@Service
public class CharacterService  {

    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterService.class);
    private String endpoint;

    private RestUtil restUtil;

    private ObjectMapper objectMapper;

    public CharacterService(@Value("${marvel_endpoint}") String endpoint, RestUtil restUtil){
        this.endpoint = endpoint;
        this.restUtil = restUtil;
        objectMapper = new ObjectMapper();
    }



    public CharacterInfo getCharacterDetails(Long id) throws ApplicationException, ServiceException {
        CharacterInfo characterInfo = null;
        String url = endpoint+"/"+id;
            try {
                ResponseEntity<String> response = restUtil.getStringResponseEntity(url);
                if (response.getStatusCode().is2xxSuccessful()) {
                    JsonNode responseData = objectMapper.readTree(response.getBody());
                    JsonNode dataNode = responseData.get(DATA);
                    ArrayNode resultArray = (ArrayNode) dataNode.get(RESULTS);
                    JsonNode characterDetailNode = resultArray.get(0);
                    characterInfo = new CharacterInfo();
                    characterInfo.setId(characterDetailNode.get(ID).asText());
                    characterInfo.setName(characterDetailNode.get(NAME).asText());
                    characterInfo.setDescription(characterDetailNode.get(DESCRIPTION).asText());
                    Thumbnail thumbnail = new Thumbnail();
                    thumbnail.setPath(characterDetailNode.get(THUMBNAIL).get(PATH).asText());
                    thumbnail.setExtension(characterDetailNode.get(THUMBNAIL).get(EXTENSION).asText());
                    characterInfo.setThumbnail(thumbnail);
                }
            }catch(HttpClientErrorException ex){
                LOGGER.error("HttpClient Error while submitting API request "+ex.getMessage());
                throw new ServiceException(ex.getRawStatusCode());
            }catch(Exception ex){
                LOGGER.error("Error while submitting API request "+ex.getMessage());
               throw new ApplicationException("Error while submitting API request " + ex.getMessage());
            }
       return characterInfo;
    }
}
