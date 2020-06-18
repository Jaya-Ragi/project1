package com.marvelous.games.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvelous.games.models.Marvelous;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class MClients {

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    ObjectMapper mapper ;


    String avengers = "http://www.mocky.io/v2/5ecfd5dc3200006200e3d64b" ;
    String anti_heroes =  "http://www.mocky.io/v2/5ecfd630320000f1aee3d64d";
    String mutants= "http://www.mocky.io/v2/5ecfd6473200009dc1e3d64e";

    @Async
    public ResponseEntity<String> getAvengersCharacter(){
        ResponseEntity<String> responseCharacter = restTemplate.exchange(anti_heroes, HttpMethod.GET, null, String.class);
        File antiHeroesFile = new File("classpath:marvelous/avengers.json");
        CopyOnWriteArrayList<Marvelous> antiHeroesList = busineesRules(responseCharacter, antiHeroesFile);
        return responseCharacter;
    }

    @Async
    public ResponseEntity<String> getAntiHeroesCharacter(){
        ResponseEntity<String> responseCharacter = restTemplate.exchange(avengers, HttpMethod.GET,null, String.class);
        File antiHeroesFile = new File("classpath:marvelous/antiHeroes.json");
        CopyOnWriteArrayList<Marvelous> antiHeroesList= busineesRules(responseCharacter,antiHeroesFile);
        return responseCharacter;
    }


    @Async
    public ResponseEntity<String> getMutants() {
        ResponseEntity<String> responseCharacter = restTemplate.exchange(mutants, HttpMethod.GET, null, String.class);
        File antiHeroesFile = new File("classpath:marvelous/mutants.json");
        CopyOnWriteArrayList<Marvelous> antiHeroesList = busineesRules(responseCharacter, antiHeroesFile);
        return responseCharacter;
    }


    public CopyOnWriteArrayList<Marvelous> busineesRules(ResponseEntity<String> responseAntiHeroesCharacter,File filePath){
        CopyOnWriteArrayList<Marvelous> antiHeroesList=null;
        try {
            if(responseAntiHeroesCharacter!=null && responseAntiHeroesCharacter.hasBody()) {
                String antiHeroesResponse= responseAntiHeroesCharacter.getBody();
                List<Marvelous> antiHeroesInfo = Arrays.asList(mapper.readValue(antiHeroesResponse, Marvelous[].class));
                antiHeroesList = new CopyOnWriteArrayList<Marvelous>(antiHeroesInfo);
                List<Marvelous> antiHeroesCharacter = (List<Marvelous>) mapper.readValue(filePath, Marvelous.class);
                if (antiHeroesCharacter == null) {
                    mapper.writeValue(filePath , antiHeroesList);
                } else {
                    for(Marvelous marvelous:antiHeroesList){
                        for(Marvelous antiHeroes :antiHeroesCharacter){
                            if(marvelous.getName().equals(antiHeroes.getName()) && !marvelous.getMax_power().equals(antiHeroes.getMax_power())){
                                marvelous.setMax_power(marvelous.getMax_power());
                            }if(!marvelous.getName().equals(antiHeroes.getName())){
                                String min_power = "0";
                                int power = Math.min(Integer.parseInt(min_power),Integer.parseInt(marvelous.getMax_power()) );
                                marvelous.setMax_power(String.valueOf(power));
                            }

                        }
                    }
                    mapper.writeValue(filePath, antiHeroesList);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return antiHeroesList;
    }
}
