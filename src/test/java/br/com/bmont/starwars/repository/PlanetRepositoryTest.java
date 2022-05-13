package br.com.bmont.starwars.repository;

import br.com.bmont.starwars.model.Planet;
import br.com.bmont.starwars.util.PlanetCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


@DataJpaTest
@DisplayName("Tests for Planet Repository")
class PlanetRepositoryTest {
    @Autowired
    private PlanetRepository planetRepository;

    @BeforeEach
    void init(){

    }

    @Test
    void findById_ReturnsPlanet_WhenSuccessful(){
        Planet planetSaved = planetRepository.save(PlanetCreator.createPlanetWithoutId());
        Optional<Planet> planetFound = planetRepository.findById(planetSaved.getId());
        Assertions.assertNotNull(planetFound);
        Assertions.assertEquals(planetFound.get().getId(), planetSaved.getId());
        Assertions.assertEquals(planetFound.get().getName(), planetSaved.getName());
        Assertions.assertEquals(planetFound.get().getClimate(), planetSaved.getClimate());
        Assertions.assertEquals(planetFound.get().getTerrain(), planetSaved.getTerrain());
    }

    @Test
    void findById_ReturnsEmpty_WhenPlanetIsNotFound(){
        Optional<Planet> planet = planetRepository.findById(2L);
        Assertions.assertTrue(planet.isEmpty());
    }

    @Test
    void findByName_ReturnsPlanet_WhenSuccessful(){
        Planet planetSaved = planetRepository.save(PlanetCreator.createPlanetWithoutId());
        Planet planetFound = planetRepository.findByName(planetSaved.getName());
        Assertions.assertNotNull(planetFound);
        Assertions.assertEquals(planetFound.getId(), planetSaved.getId());
        Assertions.assertEquals(planetFound.getName(), planetSaved.getName());
        Assertions.assertEquals(planetFound.getClimate(), planetSaved.getClimate());
        Assertions.assertEquals(planetFound.getTerrain(), planetSaved.getTerrain());
    }

    @Test
    void findByName_ReturnsNull_WhenPlanetIsNotFound(){
        Planet planetFound = planetRepository.findByName("example");
        Assertions.assertNull(planetFound);
    }

    @Test
    void findAll_ReturnsPageOfPlanet_WhenSuccessful(){
        Planet planetSaved = planetRepository.save(PlanetCreator.createPlanetWithoutId());
        Pageable pageable = PageRequest.of(0,10);
        Page<Planet> page = planetRepository.findAll(pageable);
        Planet firstPlanet = page.toList().get(0);
        Assertions.assertNotNull(page);
        Assertions.assertEquals(firstPlanet, planetSaved);
    }

    @Test
    void save_PersistPlanet_WhenSuccessful(){
        Planet planet = PlanetCreator.createPlanetWithoutId();
        Planet planetSaved = planetRepository.save(planet);
        Assertions.assertNotNull(planetSaved);
        Assertions.assertNotNull(planetSaved.getId());
        Assertions.assertEquals(planetSaved.getName(), planet.getName());
        Assertions.assertEquals(planetSaved.getClimate(), planet.getClimate());
        Assertions.assertEquals(planetSaved.getTerrain(), planet.getTerrain());
        Assertions.assertEquals(planetSaved.getMovieAppearances(), planet.getMovieAppearances());
    }

    @Test
    void delete_RemovesPlanet_WhenSuccessful(){
        Planet planetSaved = planetRepository.save(PlanetCreator.createPlanetWithoutId());
        planetRepository.delete(planetSaved);
        Optional<Planet> planetDeleted = planetRepository.findById(planetSaved.getId());

        Assertions.assertEquals(planetDeleted.isEmpty(), true);
    }
}