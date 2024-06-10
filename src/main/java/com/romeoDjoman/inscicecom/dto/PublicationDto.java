package com.romeoDjoman.inscicecom.dto;

import com.romeoDjoman.inscicecom.ennum.CategoryType;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class PublicationDto {
    private String title;
    private String abstractText;
    private String publisher;
    private List<String> keywords;
    private String language;
    private Date publicationDate;
    private String cover;
    private float price;
    private String publicationType;
    private Set<String> categories; // Utilise les noms des catégories
    private int videoLength; // Spécifique à Video
    private String videoFormat; // Spécifique à Video
    private List<String> resolution; // Spécifique à Video
    private Date creationDate; // Champ commun
    private int pageCount; // Spécifique à Document
}
