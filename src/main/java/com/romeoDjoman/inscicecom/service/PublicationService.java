package com.romeoDjoman.inscicecom.service;

import com.romeoDjoman.inscicecom.dto.PublicationDto;
import com.romeoDjoman.inscicecom.ennum.PublicationType;
import com.romeoDjoman.inscicecom.entity.Category;
import com.romeoDjoman.inscicecom.entity.Publication;
import com.romeoDjoman.inscicecom.entity.Video;
import com.romeoDjoman.inscicecom.entity.Document;
import com.romeoDjoman.inscicecom.repository.CategoryRepository;
import com.romeoDjoman.inscicecom.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public PublicationService(PublicationRepository publicationRepository, CategoryRepository categoryRepository) {
        this.publicationRepository = publicationRepository;
        this.categoryRepository = categoryRepository;
    }

    public Publication createPublication(PublicationDto publicationDto) {
        Publication publication;

        // Determine the publication type and create corresponding entity
        if ("VIDEO".equalsIgnoreCase(publicationDto.getPublicationType())) {
            publication = mapToVideo(publicationDto);
        } else if ("DOCUMENT".equalsIgnoreCase(publicationDto.getPublicationType())) {
            publication = mapToDocument(publicationDto);
        } else {
            throw new IllegalArgumentException("Invalid publication type");
        }

        // Set common fields
        publication.setTitle(publicationDto.getTitle());
        publication.setAbstractText(publicationDto.getAbstractText());
        publication.setPublisher(publicationDto.getPublisher());
        publication.setKeywords(publicationDto.getKeywords()); // Set the keywords
        publication.setLanguage(publicationDto.getLanguage());
        publication.setPublicationDate(publicationDto.getPublicationDate());
        publication.setCover(publicationDto.getCover());
        publication.setPrice(publicationDto.getPrice());

        // Map category names to Category entities
        Set<Category> categories = new HashSet<>();
        for (String categoryName : publicationDto.getCategories()) {
            Category category = categoryRepository.findByName(categoryName)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category name: " + categoryName));
            categories.add(category);
        }
        publication.setCategories(categories);

        return publicationRepository.save(publication);
    }

    private Video mapToVideo(PublicationDto publicationDto) {
        Video video = new Video();
        video.setVideoLength(publicationDto.getVideoLength());
        video.setVideoFormat(publicationDto.getVideoFormat());
        video.setResolution(publicationDto.getResolution());
        video.setCreationDate(publicationDto.getCreationDate());
        video.setPublicationType(PublicationType.VIDEO); // Ensure the type is set
        return video;
    }

    private Document mapToDocument(PublicationDto publicationDto) {
        Document document = new Document();
        document.setPageCount(publicationDto.getPageCount());
        document.setCreationDate(publicationDto.getCreationDate());
        document.setPublicationType(PublicationType.DOCUMENT); // Ensure the type is set
        return document;
    }


    public Optional<Publication> getPublicationById(int publicationById) {
        return publicationRepository.findById(publicationById);
    }
}
