package com.yapily.tech.assess.controller;

import com.yapily.tech.asses.controller.MarvelCharacterController;
import com.yapily.tech.asses.dto.CharacterInfo;
import com.yapily.tech.asses.dto.Thumbnail;
import com.yapily.tech.asses.exception.ApplicationException;
import com.yapily.tech.asses.exception.ServiceException;
import com.yapily.tech.asses.service.CharacterService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MarvelCharacterControllerTest {

    private MarvelCharacterController marvelCharacterController;

    @Mock
    private CharacterService mockCharacterService;

    @Before
    public void setup(){
        marvelCharacterController = new MarvelCharacterController(mockCharacterService);
    }

    @Test
    public void success_flow_for_loading_of_character_id() throws IOException {
      String expectedValue = new String(Files.readAllBytes(Paths.get("output.json")));
       String actualValue =  marvelCharacterController.getCharacterId();
        assertNotNull(actualValue);
        Assert.assertEquals(expectedValue,actualValue);
    }

    @Test
    public void success_flow_for_retrieving_character_info_based_on_character_id() throws IOException, ServiceException, ApplicationException {
        Long id = Long.valueOf(1234564656);
        CharacterInfo characterInfo = new CharacterInfo();
        Thumbnail thumbnail = new Thumbnail();
        characterInfo.setId("1234564656");
        characterInfo.setDescription("");
        characterInfo.setName("Test-Marvel");
        characterInfo.setThumbnail(thumbnail);
        when(mockCharacterService.getCharacterDetails(id)).thenReturn(characterInfo);

        ResponseEntity<CharacterInfo> actualValue =  marvelCharacterController.getCharacterInfo(id);
        assertNotNull(actualValue);
        Assert.assertEquals(200,actualValue.getStatusCodeValue());
        Assert.assertEquals(characterInfo,actualValue.getBody());
    }

}
