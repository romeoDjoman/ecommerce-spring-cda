package com.romeoDjoman.inscicecom.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class VideoDto extends PublicationDto {
    private int videoLength;
    private String videoFormat;
    private String resolution;
    private Date creationDate;
}
