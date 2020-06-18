package com.marvelous.games.controllers;

import com.marvelous.games.models.Marvelous;
import com.marvelous.games.service.MClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/marvelous")
public class ConsumeApis {

    @Autowired
    MClients marvelousClients;

    @GetMapping("/apis")
    public ResponseEntity<String> callMarvelousHerosInfo(){
        List<CompletableFuture> list= new ArrayList();

        ResponseEntity<String> avengers= marvelousClients.getAvengersCharacter();

        ResponseEntity<String> antiHeroes= marvelousClients.getAntiHeroesCharacter();

        ResponseEntity<String> mutantsCharacter= marvelousClients.getMutants();

        return  avengers;

    }
}
