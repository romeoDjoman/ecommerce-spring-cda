package com.romeoDjoman.inscicecom.controller;

import com.romeoDjoman.inscicecom.dto.PublicationDto;
import com.romeoDjoman.inscicecom.entity.Publication;
import com.romeoDjoman.inscicecom.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/publications")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    // Create a publication
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Publication createPublication(@RequestBody PublicationDto publicationDto) {
        return publicationService.createPublication(publicationDto);
    }

    // Show détail publication
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Publication> getPublicationById(@PathVariable("id") int id) {
        return publicationService.findPublicationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}

