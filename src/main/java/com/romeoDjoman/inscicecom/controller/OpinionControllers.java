package com.romeoDjoman.inscicecom.controller;

import com.romeoDjoman.inscicecom.entity.Opinion;
import com.romeoDjoman.inscicecom.service.OpinionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/opinion")
public class OpinionControllers {

    private final OpinionService opinionService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createOpinion(@RequestBody Opinion opinion) {
        this.opinionService.createOpinion(opinion);
    }

    @GetMapping
    public List<Opinion> listOpinion() {
        return this.opinionService.listOpinion();
    }
}
