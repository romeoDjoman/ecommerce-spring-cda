package com.romeoDjoman.inscicecom.repository;

import com.romeoDjoman.inscicecom.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Integer> {

    Optional<Publication> findByPublicationId(int publicationId);

}
