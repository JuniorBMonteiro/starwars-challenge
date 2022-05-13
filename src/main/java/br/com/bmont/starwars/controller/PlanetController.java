package br.com.bmont.starwars.controller;

import br.com.bmont.starwars.dto.PlanetDTO;
import br.com.bmont.starwars.model.Planet;
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
    public ResponseEntity<Planet> addPlanet(@RequestBody @Valid PlanetDTO planetDTO){
        return new ResponseEntity<>(planetService.addPlanet(planetDTO), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<Page<Planet>> listAll(Pageable pageable){
        return new ResponseEntity<>(planetService.listAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<Planet> getByName(@RequestParam String name){
        return new ResponseEntity<>(planetService.getByName(name), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> getById(@PathVariable long id){
        return new ResponseEntity<>(planetService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanet(@PathVariable long id){
        planetService.deletePlanetById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
