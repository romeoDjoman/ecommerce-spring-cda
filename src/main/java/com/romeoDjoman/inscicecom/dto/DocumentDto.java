package com.romeoDjoman.inscicecom.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class DocumentDto extends PublicationDto{
    private int pageCount;
    private Date creationDate;
}
