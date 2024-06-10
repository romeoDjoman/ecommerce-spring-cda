package com.romeoDjoman.inscicecom.entity;

import com.romeoDjoman.inscicecom.ennum.PublicationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "videos")
@DiscriminatorValue("VIDEO")
public class Video extends Publication {
    private int videoLength;
    private String videoFormat;
    private List<String> resolution;
    private Date creationDate;


    public Video() {
        this.setPublicationType(PublicationType.VIDEO);
    }
}
