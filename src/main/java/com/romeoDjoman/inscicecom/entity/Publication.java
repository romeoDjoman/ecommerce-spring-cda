package com.romeoDjoman.inscicecom.entity;

import com.romeoDjoman.inscicecom.ennum.CategoryType;
import com.romeoDjoman.inscicecom.ennum.PublicationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "publications")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "publicationType", discriminatorType = DiscriminatorType.STRING)
public abstract class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int publicationId;
    private String title;
    private String abstractText;
    private String publisher;
    @ElementCollection
    private List<String> keywords;
    private String language;
    private Date publicationDate;
    private String cover;
    private float price;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    private PublicationType publicationType;

    @ManyToMany
    @JoinTable(
            name = "publication_category",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    // Les relations avec Cart et Comment sont commentées pour simplification
    // @ManyToMany(mappedBy = "publications")
    // private List<Cart> carts;

    // @OneToMany(mappedBy = "publication")
    // private List<Comment> comments;
}