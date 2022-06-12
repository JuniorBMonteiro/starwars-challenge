package br.com.bmont.starwars.controller;

import br.com.bmont.starwars.request.PlanetRequest;
import br.com.bmont.starwars.model.Planet;
import br.com.bmont.starwars.response.PlanetResponse;
import br.com.bmont.starwars.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/planets")
@RequiredArgsConstructor
public class PlanetController {
    private final PlanetService planetService;

    @PostMapping
    public ResponseEntity<PlanetResponse> addPlanet(@RequestBody @Valid PlanetRequest planetRequest){
        return new ResponseEntity<>(planetService.addPlanet(planetRequest), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<Page<PlanetResponse>> listAll(Pageable pageable){
        return new ResponseEntity<>(planetService.listAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PlanetResponse> getByName(@PathVariable String name){
        return new ResponseEntity<>(planetService.getByName(name), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanetResponse> getById(@PathVariable long id){
        return new ResponseEntity<>(planetService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanet(@PathVariable long id){
        planetService.deletePlanetById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
