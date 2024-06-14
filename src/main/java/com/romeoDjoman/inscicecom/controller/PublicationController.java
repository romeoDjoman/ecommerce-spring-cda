package com.romeoDjoman.inscicecom.controller;

import com.romeoDjoman.inscicecom.dto.PublicationDto;
import com.romeoDjoman.inscicecom.entity.Publication;
import com.romeoDjoman.inscicecom.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    // Create a publication
    @PostMapping(path = "publications")
    public Publication createPublication(@RequestBody PublicationDto publicationDto) {
        return publicationService.createPublication(publicationDto);
    }

    // Show détail publication
    @GetMapping(path = "publications/{id}")
    public ResponseEntity<Publication> getPublicationById(@PathVariable int id) {
        Optional<Publication> publication = publicationService.getPublicationById(id);
        return publication.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

