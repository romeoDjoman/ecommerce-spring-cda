package com.romeoDjoman.inscicecom.service;

import com.romeoDjoman.inscicecom.entity.User;
import com.romeoDjoman.inscicecom.entity.Opinion;
import com.romeoDjoman.inscicecom.repository.OpinionRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OpinionService {

    private final OpinionRepository opinionRepository;

    public void createOpinion(Opinion opinion) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        opinion.setUser(user);
        this.opinionRepository.save(opinion);
    }

    public List<Opinion> listOpinion() {
        return (List<Opinion>) this.opinionRepository.findAll();
    }
}
