package com.romeoDjoman.inscicecom.entity;

import com.romeoDjoman.inscicecom.ennum.PublicationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "documents")
@DiscriminatorValue("DOCUMENT")
public class Document extends Publication {
    private int pageCount;
    private Date creationDate;

    public Document() {
        this.setPublicationType(PublicationType.DOCUMENT);
    }
}

