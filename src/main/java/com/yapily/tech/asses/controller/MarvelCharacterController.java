package com.yapily.tech.asses.controller;


import com.yapily.tech.asses.dto.CharacterInfo;
import com.yapily.tech.asses.exception.ApplicationException;
import com.yapily.tech.asses.exception.ServiceException;
import com.yapily.tech.asses.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping(path = "/characters")
public class MarvelCharacterController {

    private CharacterService characterService;

    public MarvelCharacterController(CharacterService characterService){
        this.characterService = characterService;
    }



    @GetMapping(path="/",produces = "application/json")
    public String getCharacterId() throws IOException {
        return new String(Files.readAllBytes(Paths.get("output.json")));
    }


    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<CharacterInfo> getCharacterInfo(@PathVariable Long id) throws ApplicationException, ServiceException {
        CharacterInfo characterInfo = characterService.getCharacterDetails(id);
       return new ResponseEntity<CharacterInfo>(characterInfo, HttpStatus.OK);
    }
}
