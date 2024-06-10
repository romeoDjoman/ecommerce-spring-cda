package com.romeoDjoman.inscicecom.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class VideoDto extends PublicationDto{

    private int videoLength;
    private String videoFormat;
    private List<String> resolution;
    private Date creationDate;
}
