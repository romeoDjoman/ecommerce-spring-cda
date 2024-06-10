package com.romeoDjoman.inscicecom.controller;

import com.romeoDjoman.inscicecom.dto.PublicationDto;
import com.romeoDjoman.inscicecom.entity.Publication;
import com.romeoDjoman.inscicecom.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @PostMapping(path = "publications")
    public Publication createPublication(@RequestBody PublicationDto publicationDto) {
        return publicationService.createPublication(publicationDto);
    }
}

